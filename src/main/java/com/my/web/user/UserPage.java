package com.my.web.user;

import com.my.db.DBException;
import com.my.db.DBManager;
import com.my.db.entity.*;
import com.my.utilit.Rounder;
import com.my.web.admin.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.my.constant.AppConstant.EXCEPTION;

@WebServlet("/userPage")
public class UserPage extends HttpServlet {
    private final Logger log = LogManager.getLogger(Login.class);

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doGet(req, resp);
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        List<Service> serviceL;
        String login = (String)session.getAttribute("login");
        try {
            DBManager dbManager = DBManager.getDbManager();
            User user = dbManager.getUser(login);
            session.setAttribute("user", user);
            session.setAttribute("userCash", Rounder.roundValue(user.getCash()));
            serviceL = dbManager.getService(user);
            session.setAttribute("serviceL", serviceL);
            List<ViewService> serviceView = dbManager.getUserService(serviceL);
            session.setAttribute("userService", serviceView);
            resp.sendRedirect("userPage.jsp");
        } catch (DBException ex) {
            log.error(EXCEPTION, "can't select user in servlet userPage", ex);
            session.setAttribute("content", "system.err");
            resp.sendRedirect("errorPage.jsp");
        } catch (IllegalStateException ex) {
            log.error(EXCEPTION, "can't init Db", ex);
            session.setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }
    }
}