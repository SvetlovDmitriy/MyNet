package com.my.lang;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/*
 * This class changes the language on the page.
 */

@WebServlet("/lang")
public class Lan extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        String lang = (String) session.getAttribute("lang");
        if (lang == null) {
            session.setAttribute("lang", "");
        }
        session.setAttribute("lang", req.getParameter("lang"));
        String referer = null;
        try {
            referer = new URI(req.getHeader("referer")).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(referer);
        resp.sendRedirect(referer);
    }
}