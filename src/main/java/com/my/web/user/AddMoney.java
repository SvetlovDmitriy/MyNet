package com.my.web.user;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.my.constant.AppConstant.EXCEPTION;
import static com.my.constant.AppConstant.FLOW;

@WebServlet("/addMoney")
public class AddMoney extends HttpServlet {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println(user);
        user.setCash(user.getCash() + Double.parseDouble(req.getParameter("cash")));
        try {
            DBManager dbManager = DBManager.getDbManager();
            dbManager.updateCash(user);
            session.setAttribute("user", user);
            log.info(FLOW, user + " add cash");
            resp.sendRedirect("userPage");
        } catch (DBException ex) {
            req.getSession().setAttribute("content", "system.err");
            resp.sendRedirect("errorPage.jsp");
            log.error(EXCEPTION, "can't find user. Servlet findAllProduct", ex);
        } catch (IllegalStateException ex) {
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }
    }
}
