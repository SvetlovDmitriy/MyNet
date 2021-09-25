package com.my.web.admin;

import com.my.db.DBException;
import com.my.db.DBManager;
import com.my.db.entity.Category;
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

@WebServlet("/addCategory")
public class AddCategory extends HttpServlet {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String categoryName;
        HttpSession session = req.getSession();
        categoryName = req.getParameter("categoryName");
        try {
            DBManager dbManager = DBManager.getDbManager();
            Category category = dbManager.addCategory(categoryName);
            resp.sendRedirect("findAllCategory");
        } catch (DBException ex) {
            log.error(EXCEPTION, "can't add category in servlet AddCategory", ex);
            req.getSession().setAttribute("content", "cantAddCategory");
            resp.sendRedirect("errorPage.jsp");
        }
        catch (IllegalStateException ex){
            log.error(EXCEPTION, "can't connect to db", ex);
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }
    }
}
