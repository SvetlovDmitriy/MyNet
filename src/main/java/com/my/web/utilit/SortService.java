package com.my.web.utilit;

import com.my.db.entity.Product;
import com.my.utilit.Sorter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/sortService")
public class SortService extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String sortTyp = req.getParameter("sortTyp");
        HttpSession session = req.getSession();
        List<Product> productL = (List<Product>) session.getAttribute("productL");
        if ("price".equals(sortTyp)){
            Sorter.sortByPrice(productL);
        } else if ("priceRevers".equals(sortTyp)){
            Sorter.sortByPriceRevers(productL);
        } else if ("name".equals(sortTyp)){
            Sorter.sortByName(productL);
        } else if ("nameRevers".equals(sortTyp)){
            Sorter.sortByNameRevers(productL);
        }
        session.setAttribute("productL", productL);
        if ("admin".equals(session.getAttribute("role"))){
            resp.sendRedirect("adminProduct.jsp");
        } else {
            resp.sendRedirect("userProduct.jsp");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }
}
