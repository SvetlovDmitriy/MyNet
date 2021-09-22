package com.my.web.listener;

import javax.servlet.*;
import javax.servlet.annotation.*;

import org.apache.logging.log4j.*;

@WebListener
public class LogPathCreator implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("LogPathCreator");
        ServletContext ctx = sce.getServletContext();
        String path = ctx.getRealPath("/WEB-INF/logInfo.log");
        System.setProperty("logInfo", path);
        path = ctx.getRealPath("/WEB-INF/logErr.log");
        System.setProperty("logErr", path);
    }
}

