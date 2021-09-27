package com.my.web.admin;

import com.my.db.DBException;
import com.my.db.DBManager;
import com.my.db.entity.User;
import com.my.db.sqlworker.MySqlWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.my.constant.AppConstant.EXCEPTION;

//@WebServlet("/findAllUserLimit")
public class FindAllUserLimit extends HttpServlet {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        int page = 1;
        int recordsPerPage;
        if (session.getAttribute("recordsPerPage") == null){
            session.setAttribute("recordsPerPage",
                    Integer.parseInt(getInitParameter("recordsPerPage")));
        }
        if(req.getParameter("page") != null){
            page = Integer.parseInt(req.getParameter("page"));
        }
        if(req.getParameter("recordsPerPage") != null){
            recordsPerPage = Integer.parseInt(req.getParameter("recordsPerPage"));
            session.setAttribute("recordsPerPage", recordsPerPage);
        } else {
            recordsPerPage = (int)session.getAttribute("recordsPerPage");
        }
        try {
            DBManager dbManager = DBManager.getDbManager();
            List<User> userL = dbManager.findAllUserLimit((page - 1) * recordsPerPage, recordsPerPage);
            System.out.println("userL = " + userL);
            int noOfRecords = dbManager.getTotalUser();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            System.out.println("total user = " + noOfRecords);
            session.setAttribute("userL", userL);
            session.setAttribute("noOfPages", noOfPages);
            session.setAttribute("currentPage", page);
            resp.sendRedirect("viewAllUserLimit.jsp");
        } catch (DBException ex) {
            log.error(EXCEPTION, "can't find user. Servlet findAllProduct", ex);
            req.getSession().setAttribute("content", "system.err");
            resp.sendRedirect("errorPage.jsp");
        }
        catch (IllegalStateException ex) {
            log.error(EXCEPTION, "can't connect to db", ex);
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }
    }
}
