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
import java.util.List;

import static com.my.constant.AppConstant.EXCEPTION;
import static com.my.constant.AppConstant.FLOW;

@WebServlet("/deleteProduct")
public class DeleteProduct extends HttpServlet {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int productId = Integer.parseInt(req.getParameter("idp"));
        String productName = req.getParameter("namep");
        List<User> userL;
        try {
            DBManager dbManager = DBManager.getDbManager();
            userL = dbManager.findAllUser(productId);
            if (userL.isEmpty()){
                dbManager.deleteProduct(productId);
                log.info(FLOW, "delete product name = " + productName);
                resp.sendRedirect("findAllProduct");
            } else{
                req.getSession().setAttribute("userL", userL);
                resp.sendRedirect("showExistUser.jsp");
            }
        } catch (DBException ex) {
            log.error(EXCEPTION, "can't delete product in servlet DeleteProduct", ex);
            req.getSession().setAttribute("content", "deleteProduct");
            req.getRequestDispatcher("blankPage.jsp").forward(req, resp);
        } catch (IllegalStateException ex) {
            log.error(EXCEPTION, "can't connect to db", ex);
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
