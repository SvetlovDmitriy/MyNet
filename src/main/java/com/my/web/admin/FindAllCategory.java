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
import java.util.List;

import static com.my.constant.AppConstant.EXCEPTION;

@WebServlet("/findAllCategory")
public class FindAllCategory extends HttpServlet {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        DBManager dbManager;
        HttpSession session = req.getSession();
        try {
            dbManager = DBManager.getDbManager();
            List<Category> categoryL = dbManager.findAllCategory();
            req.getSession().setAttribute("categoryL", categoryL);
            if ("admin".equals(session.getAttribute("role"))){
                resp.sendRedirect("adminCategory.jsp");
            } else {
                resp.sendRedirect("userCategory.jsp");
            }
        } catch (DBException ex) {
            log.error(EXCEPTION, "can't find category in servlet findAllCategory", ex);
            req.getSession().setAttribute("content", "system.err");
            resp.sendRedirect("errorPage.jsp");
        }
        catch (IllegalStateException ex){
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }
    }
}
