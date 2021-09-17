package com.my.web.listener;

import com.my.db.DBException;
import com.my.db.DBManager;
import com.my.db.entity.Product;
import com.my.db.entity.Service;
import com.my.db.entity.User;
import com.my.db.entity.TimeT;
import com.my.db.sqlworker.MySqlWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static com.my.constant.AppConstant.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class DaemonListener implements ServletContextListener {
    private volatile boolean active = true;
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    Runnable myDeamon = new Runnable() {
        public void run() {
            while (active) {
//                Date currentDate = new Date();
//                DateFormat timeFormat = new SimpleDateFormat("HHmmss", Locale.getDefault());
//                String timeText = timeFormat.format(currentDate);
//                System.out.println(timeText);
                try {
                    System.out.println("Deamon run");
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    log.error(EXCEPTION, "thread is close in class DaemonListener");
                }
                try {
                    DBManager dbManager = DBManager.getDbManager();
                    List<Service> serviceL= dbManager.findAllService();
                    for(Service service : serviceL){
                        Product product = dbManager.getProduct(service.getProductId());
                        User user = dbManager.getUser(service.getUserId());
                        TimeT timeT = dbManager.findTime(service.getId());
                        if (service.getStatusId() == 2){
                            user.setCash(user.getCash() - product.getPrice()/5 * timeT.getTotal());
                        } else {
                            Date date= new Date();
                            long time = date.getTime();
                            long total = (time - timeT.getTime().getTime())/1000 + timeT.getTotal();
                            System.out.println("total time = " + total);
                            user.setCash(user.getCash() - product.getPrice()/5 * total);
                        }
                        dbManager.updateCashTimeT(user, timeT);
                    }
                } catch (IllegalStateException ex) {
                    log.error(EXCEPTION, "can't connect to db", ex);
                } catch (DBException ex) {
                    log.error(EXCEPTION, "can't withdraw money", ex);
                }
            }
        }
    };

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        new Thread(myDeamon).start();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        active = false;
    }
}