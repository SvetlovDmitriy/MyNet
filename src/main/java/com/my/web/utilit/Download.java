package com.my.web.utilit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet("/download")
public class Download extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setHeader("Content-disposition", "attachment; filename=MyNet Price list.txt");
        InputStream in =
                req.getServletContext().getResourceAsStream("Price list.txt");
        if (in == null){
            req.getSession().setAttribute("content", "fileNotExist");
            resp.sendRedirect("blankPage.jsp");
        } else {
            OutputStream out = resp.getOutputStream();
            byte[] buffer = new byte[1000];
            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
            out.close();
            in.close();
            resp.sendRedirect("guest/guestHome.jsp");
        }
    }
}
