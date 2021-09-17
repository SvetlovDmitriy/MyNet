package com.my.db.dbUtilit;

import com.my.db.sqlworker.MySqlWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import static com.my.constant.AppConstant.EXCEPTION;

public class DbUtilit {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    public static void close(AutoCloseable ... ac){
        if (ac != null) {
            try {
                for(AutoCloseable a : ac){
                    if(a != null){
                        a.close();
                    }
                }
            } catch (Exception ex) {
                log.error(EXCEPTION, "Don't close connection in metho close", ex);
            }
        }
    }

    public static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                log.error(EXCEPTION, "Don't do rollbak in method rollback", ex);
            }
        }
    }
}
