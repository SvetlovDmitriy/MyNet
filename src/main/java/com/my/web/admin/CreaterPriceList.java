package com.my.web.admin;

import com.my.db.DBException;
import com.my.db.DBManager;
import com.my.db.entity.Category;
import com.my.db.entity.Product;
import com.my.db.sqlworker.MySqlWorker;
import com.my.utilit.CreateFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.my.constant.AppConstant.EXCEPTION;

@WebServlet("/createPriceList")
public class CreaterPriceList extends HttpServlet {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DBManager dbManager = DBManager.getDbManager();
            Map<Category, List<Product>> priceList = dbManager.getPriceList();
            String path = getServletContext().getRealPath("/Price list.txt");
            CreateFile.createFile(priceList, path);
            req.getSession().setAttribute("content", "priceListCompleted");
            resp.sendRedirect("blankPage.jsp");
        } catch (DBException | IOException ex) {
            log.error(EXCEPTION, "can't create price list in servlet createPriceList", ex);
            req.getSession().setAttribute("content", "notCreatePriceList");
            resp.sendRedirect("errorPage.jsp");
        } catch (IllegalStateException ex) {
            log.error(EXCEPTION, "can't connect to db", ex);
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }
    }
}
