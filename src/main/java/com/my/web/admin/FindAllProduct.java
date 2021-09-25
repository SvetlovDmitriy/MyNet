package com.my.web.admin;

import com.my.db.DBException;
import com.my.db.DBManager;
import com.my.db.entity.Product;
import com.my.db.sqlworker.MySqlWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.my.constant.AppConstant.EXCEPTION;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/findAllProduct")
public class FindAllProduct extends HttpServlet {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int categoryId;
        HttpSession session = req.getSession();
        if (req.getParameter("categoryId") != null) {
            categoryId = Integer.parseInt(req.getParameter("categoryId"));
            session.setAttribute("categoryId", categoryId);
        } else {
            categoryId = (Integer) (session.getAttribute("categoryId"));
        }
        try {
            DBManager dbManager = DBManager.getDbManager();
            List<Product> productL = dbManager.findAllProduct(categoryId);
            req.getSession().setAttribute("productL", productL);
            System.out.println(productL);
            req.getSession().setAttribute("productL", productL);
            if ("admin".equals(session.getAttribute("role"))){
                resp.sendRedirect("adminProduct.jsp");
            } else {
                resp.sendRedirect("userProduct.jsp");
            }
        } catch (DBException ex) {
            log.error(EXCEPTION, "can't find product servlet findAllProduct", ex);
            req.getSession().setAttribute("content", "system.err");
            resp.sendRedirect("errorPage.jsp");
        }
        catch (IllegalStateException ex){
            log.error(EXCEPTION, "can't init Db", ex);
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }
    }
}
