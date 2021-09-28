package com.my.web.listener;

import com.my.db.DBException;
import com.my.db.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import static com.my.constant.AppConstant.EXCEPTION;

public class DaemonListener implements ServletContextListener {
    private volatile boolean active = true;
    private Logger log;

    Runnable myDaemon = new Runnable() {
        public void run() {
            while (active) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    log.error(EXCEPTION, "thread is close in class DaemonListener");
                }
                try {
                    DBManager dbManager = DBManager.getDbManager();
                    dbManager.withdrawMoney();
                } catch (IllegalStateException ex) {
                    log.error(EXCEPTION, "can't connect to db", ex);
                } catch (DBException ex) {
                    log.error(EXCEPTION, "can't withdraw money", ex);
                }
            }
        }
    };

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log = LogManager.getLogger(DaemonListener.class);
        new Thread(myDaemon).start();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        active = false;
    }
}