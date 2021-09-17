package com.my.web.listener;

import com.my.db.Worker;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InstanceWorker implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Worker.init("com.my.db.sqlworker.MySqlWorker");
    }
}
