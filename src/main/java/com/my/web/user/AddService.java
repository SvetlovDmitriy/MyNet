package com.my.web.user;

import com.my.db.DBException;
import com.my.db.DBManager;
import com.my.db.entity.Service;
import com.my.db.entity.User;
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

@WebServlet("/addService")
public class AddService extends HttpServlet {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idp = Integer.parseInt(req.getParameter("idp"));
        String namep = req.getParameter("namep");
        User user =(User)req.getSession().getAttribute("user");
        try {
            DBManager dbManager = DBManager.getDbManager();
            Service service = dbManager.addService(user.getId(), idp);
            if (service.getId() > 0){
                log.info(FLOW, user + " add service " + namep);
                resp.sendRedirect("userPage");
            }

        } catch (DBException ex) {
            log.error(EXCEPTION, "can't add service in servlet addService", ex);
            req.getSession().setAttribute("content", "addService");
            resp.sendRedirect("blankPage.jsp");
        } catch (IllegalStateException ex) {
            log.error(EXCEPTION, "can't connect to db", ex);
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }
    }
}
