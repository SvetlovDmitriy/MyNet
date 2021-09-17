package com.my.web.user;

import com.my.db.DBException;
import com.my.db.DBManager;
import com.my.db.sqlworker.MySqlWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.my.constant.AppConstant.EXCEPTION;
import static com.my.constant.AppConstant.FLOW;

@WebServlet("/deleteService")
public class DeleteService extends HttpServlet {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int serviceId = Integer.parseInt(req.getParameter("serviceId"));
        String serviceName = req.getParameter("productName");
        try {
            DBManager dbManager = DBManager.getDbManager();
            dbManager.deleteService(serviceId);
            log.info(FLOW, "User " + req.getSession().getAttribute("login") +
                    " delete service name = " + serviceName);
            resp.sendRedirect("userPage");
        } catch (DBException ex) {
            log.error(EXCEPTION, "can't delete service in servlet DeleteProduct", ex);
            req.getSession().setAttribute("content", "deleteProduct");
            resp.sendRedirect("blankPage.jsp");
        } catch (IllegalStateException ex) {
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }
    }
}
