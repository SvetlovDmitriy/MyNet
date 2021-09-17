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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.my.constant.AppConstant.EXCEPTION;

@WebServlet("/findAllUser")
public class FindAllUser extends HttpServlet {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        DBManager dbManager;
        try {
            dbManager = DBManager.getDbManager();
            List<User> userL = dbManager.findAllUser();
            System.out.println(userL);
            HttpSession session = req.getSession();
            session.setAttribute("userL", userL);
            resp.sendRedirect("viewsAllUser.jsp");
        } catch (DBException ex) {
            req.getSession().setAttribute("content", "system.err");
            resp.sendRedirect("errorPage.jsp");
            log.error(EXCEPTION, "can't find user. Servlet findAllProduct", ex);
        }
        catch (IllegalStateException ex) {
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }
    }

}
