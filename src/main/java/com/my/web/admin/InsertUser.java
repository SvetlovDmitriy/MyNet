package com.my.web.admin;

import com.my.db.DBException;
import com.my.db.DBManager;
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

import static com.my.constant.AppConstant.*;

@WebServlet("/insertuser")
public class InsertUser extends HttpServlet {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        DBManager dbManager;
        String login = req.getParameter("login");
        System.out.println(login);
        User user = new User();
        user.setLogin(login);
        user.setPassword(login);
        req.getSession().setAttribute("content", "dontInsertUser");
        try {
            dbManager = DBManager.getDbManager();
            if (dbManager.insertUser(user)){
                Logger log = LogManager.getLogger(InsertUser.class);
                log.info(FLOW, "User " + login + " add to " + MY_NET);
                req.getSession().setAttribute("content", "insertUser");
                resp.sendRedirect("blankPage.jsp");
            } else {
                req.getSession().setAttribute("content", "dontInsertUser");
                resp.sendRedirect("blankPage.jsp");
            }
        } catch (DBException ex) {
            log.error(EXCEPTION, "can't insert user in servlet InsertUser", ex);
            req.setAttribute("content", "system.err");
            req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
        }
        catch (IllegalStateException ex) {
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }
    }
}
