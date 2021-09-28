package com.my.web.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageRedirectSecurityFilter implements Filter {
    private final String startPath = "/start.jsp";
    private final String cssPath = "/css/style.css";
    private final String errorPage = "/errorPage.jsp";
    private final String blankPage = "/blankPage.jsp";
    private final String adminNotAccess = "/userPage /deleteService /addService /addMoney userPage.jsp " +
            "/userCategory.jsp" + "/userPage.jsp /userProduct.jsp";
    private final String userNotAcces = "/addCategory /addProduct /deleteCategory /deleteProduct /findAllUser " +
            "/updateProduct /insertuser /adminCategory.jsp /adminPage.jsp /adminProduct.jsp /showExistUser.jsp";
    private final String guestAccess = "/guest/guestHome.jsp /download /errorPage.jsp /blankPage.jsp /login " +
            "/lang";

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String role = (String) httpRequest.getSession().getAttribute("role");
        String contextPath = httpRequest.getContextPath();
        String uri = httpRequest.getRequestURI();
        String nameServlet = httpRequest.getServletPath();
        if (!isStartPage(contextPath, uri)) {
            if ("admin".equals(role) && (adminNotAccess.contains(nameServlet))){
                httpResponse.sendRedirect(contextPath + startPath);
                return;
            } else if ("user".equals(role) && (userNotAcces.contains(nameServlet))){
                httpResponse.sendRedirect(contextPath + startPath);
                return;
            } else if ("guest".equals(role) && (!guestAccess.contains(nameServlet))){
                httpResponse.sendRedirect(contextPath + startPath);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    /**
     *  return true when the request URI belongs start.jsp or style.jsp or errorPage.jsp or blankPaje.jsp
     */
    private boolean isStartPage(String contextPath, String uri){
        return (uri.equals(contextPath + "/")) || (uri.equals(contextPath + startPath)) ||
                (uri.equals(contextPath + cssPath) || (uri.equals(contextPath + blankPage)) ||
                uri.equals(contextPath + errorPage));
    }
}