package com.my.web.admin;

import com.my.db.DBException;
import com.my.db.DBManager;
import com.my.db.entity.Service;
import com.my.db.entity.User;
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
import static com.my.constant.AppConstant.FLOW;

@WebServlet("/updateAdmUsr")
public class UpdateAdmUsr extends HttpServlet {
    private final Logger log = LogManager.getLogger(Login.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        String str;
        HttpSession session = req.getSession();
        if (req.getParameter("serviceStatus") == null){
            str = (String)req.getSession().getAttribute("serviceStatus");
        } else{
            str = req.getParameter("serviceStatus");
            req.getSession().setAttribute("serviceStatus", str);
        }
        String[] strA = str.split("\\s");
        int status = Integer.parseInt(strA[0]);
        int serviceId = Integer.parseInt(strA[1]);
        try {
            if (user.getCash() > 0) {
                DBManager dbManager = DBManager.getDbManager();
                dbManager.updateStatus(serviceId, status);
                log.info(FLOW, "" + user + " change state of service id " +
                        serviceId + " to " + status);
            }
            if (((session.getAttribute("role"))).equals("admin")){
                resp.sendRedirect("selectuser?login=" + user.getLogin());
            } else {
                resp.sendRedirect("userPage?login=" + user.getLogin());
            }
        } catch (DBException ex) {
            log.error(EXCEPTION, "can't update in servlet updateAdmUsr", ex);
            req.getSession().setAttribute("content", "system.err");
            resp.sendRedirect("errorPage.jsp");
        } catch (IllegalStateException ex) {
            log.error(EXCEPTION, "can't connect to db", ex);
            req.getSession().setAttribute("content", "messages.noconnection");
            resp.sendRedirect("errorPage.jsp");
        }
    }
}
