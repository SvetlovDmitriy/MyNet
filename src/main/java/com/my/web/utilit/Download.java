package com.my.web.utilit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/download")
public class Download extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream in;
        String fileName = "";
        String param = req.getParameter("download");
        if ("priceList".equals(param)) {
            in = req.getServletContext().getResourceAsStream("Price list.txt");
            fileName = "MyNetPrice list.txt";
        } else if ("logInfo".equals(param)) {
            in = new FileInputStream(System.getProperty("logInfo"));
            fileName = "logInfo.txt";
        } else if ("logErr".equals(param)) {
            in = new FileInputStream(System.getProperty("logErr"));
            fileName = "logErr.txt";
        } else {
            in = null;
        }

        resp.setContentType("text/plain");
        resp.setHeader("Content-disposition", "attachment; filename=" + fileName);
        if (in == null) {
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
        }
    }
}
