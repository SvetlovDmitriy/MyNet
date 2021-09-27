package com.my.web.jstl;

import com.my.db.entity.User;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class MyTag extends TagSupport {
    private List<User> userL;

    public void setUserL(List<User> userL) {
        this.userL = userL;
    }

    public int doStartTag() throws JspTagException{
        try {
            pageContext.getOut().write("<table><caption>Users List</caption>\n" +
                    "    <thead>\n" +
                    "    <tr>\n" +
                    "        <th scope=\"col\">User id</th>\n" +
                    "        <th scope=\"col\">User login</th>\n" +
                    "        <th scope=\"col\">User cash</th>\n" +
                    "    </tr>\n" +
                    "    </thead>");
            for (User user : userL){
                pageContext.getOut().write("<tr>");
                pageContext.getOut().write("<td>" + user.getId() + "</td>");
                pageContext.getOut().write("<td><a href=\"selectuser?login=" + user.getLogin() + "\">" +
                        user.getLogin() + "</a></td>");
                pageContext.getOut().write("<td>" + user.getCash() + "</td>");
                pageContext.getOut().write("</tr>");
            }
        } catch (IOException ex){
            throw new JspTagException(ex.getMessage());
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException {
        try {
            pageContext.getOut().write("</table>");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
