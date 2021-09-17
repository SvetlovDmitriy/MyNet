package com.my.web.admin;

import com.my.db.DBException;
import com.my.db.DBManager;
import com.my.db.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.IllformedLocaleException;


import static com.my.constant.AppConstant.*;

@WebServlet("/login")
public class Login extends HttpServlet {
    private final Logger log = LogManager.getLogger(Login.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        System.out.println("before login " + req.getSession().getAttribute("role"));
        System.out.println(req.getSession());

        session.setAttribute("role", "guest");
        try {
            DBManager dbManager = DBManager.getDbManager();
            User user = dbManager.getUser(login);
            if((user != null) && (user.getPassword().equals(password))){
                log.info(FLOW, "User " + user + " logged in " + MY_NET);
                if (user.getRole() == 1)
                {
                    session.setAttribute("role", "admin");
                    resp.sendRedirect("adminPage.jsp");
                } else if (user.getRole() == 2)
                {
                    session.setAttribute("role", "user");
                    session.setAttribute("login", login);
                    resp.sendRedirect("userPage");
                }
            } else {
                resp.sendRedirect("guest/guestHome.jsp");
            }
            System.out.println("after login " + req.getSession().getAttribute("role"));
        } catch (DBException  ex) {
            log.error(EXCEPTION, "cannot connect to db");
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        } catch (IllegalStateException ex){
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }

    }
}
