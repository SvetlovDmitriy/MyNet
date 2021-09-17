package com.my.web.admin;

import com.my.db.DBException;
import com.my.db.DBManager;
import com.my.db.entity.Product;
import com.my.db.sqlworker.MySqlWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.my.constant.AppConstant.EXCEPTION;
import static com.my.constant.AppConstant.FLOW;

@WebServlet("/deleteCategory")
public class DeleteCategory extends HttpServlet {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        List<Product> productL;
        try {
            DBManager dbManager = DBManager.getDbManager();
            productL = dbManager.findAllProduct(id);
            System.out.println("productL = " + productL);
            if (productL.isEmpty()){
                dbManager.deleteCategory(id);
                log.info(FLOW, "Category " + name + " delete");
                resp.sendRedirect("findAllCategory");
            } else {
                req.getSession().setAttribute("productL", productL);
                resp.sendRedirect("showExistProduct.jsp");
            }
        } catch (DBException ex) {
            log.error(EXCEPTION, "can't delete category in servlet DeleteCategory", ex);
            req.setAttribute("content", "deleteProduct");
            resp.sendRedirect("blankPage.jsp");
        } catch (IllegalStateException ex) {
            req.setAttribute("content", "messages.noconnection");
            req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
