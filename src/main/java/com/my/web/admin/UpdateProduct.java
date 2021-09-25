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

import static com.my.constant.AppConstant.EXCEPTION;
import static com.my.constant.AppConstant.FLOW;

@WebServlet("/updateProduct")
public class UpdateProduct extends HttpServlet {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Product product = new Product();
        product.setId(Integer.parseInt(req.getParameter("id")));
        product.setName(req.getParameter("name"));
        product.setPrice(Double.parseDouble(req.getParameter("price")));
        product.setDescription(req.getParameter("des"));
        product.setCategoryId((Integer) req.getSession().getAttribute("categoryId"));
        DBManager dbManager;
        try {
            dbManager = DBManager.getDbManager();
            dbManager.updateProduct(product);
            log.info(FLOW, "Product id = " + product.getId() + "was update");
            resp.sendRedirect("findAllProduct");
        } catch (DBException ex) {
            log.error(EXCEPTION, "can't update product servlet updateProduct", ex);
            req.getSession().setAttribute("content", "system.err");
            resp.sendRedirect("errorPage.jsp");
        } catch (IllegalStateException ex) {
            log.error(EXCEPTION, "can't connect to db", ex);
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }
    }
}
