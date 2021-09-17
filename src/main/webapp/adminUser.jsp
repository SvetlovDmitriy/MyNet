<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="prop"/>
<fmt:message key="button.select" var="bSelect"/>
<fmt:message key="button.login" var="bLogin"/>
<html>
<head>
    <title>Admin-User</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="JS/test.js"></script>
</head>
<body>
<header class="header">
    <div class="header-left">
        <h1>MyNet</h1>
    </div>
    <div class="body-centre">
        <a href="adminPage.jsp" class="topic">To Admin Page</a>
    </div>
    <form class = "header-right" action="lang" method="get" id="f1"></form>
    <select name="lang" form="f1">
        <option>${sessionScope.lang}</option>
        <option value="en">en</option>
        <option value="ru">ru</option>
    </select>
    <input type="submit" value="${bSelect}" form="f1"/>
</header>

    <h1>${sessionScope.user.login}</h1>
    <p>Cash ${sessionScope.userCash}</p>

    <table>
        <caption>Services</caption>
        <thead>
        <tr>
            <th scope="col">Service category</th>
            <th scope="col">Service id</th>
            <th scope="col">Service name</th>
            <th scope="col">Service description</th>
            <th scope="col">Service price</th>
            <th scope="col">Status</th>
        </tr>
        </thead>
        <c:forEach items="${sessionScope.userService}" var="userS">
            <tr>
                <td>${userS.categoryName}</td>
                <td>${userS.serviceID}</td>
                <td>${userS.productName}</td>
                <td>${userS.productDescription}</td>
                <td>${userS.productPrice}</td>
                <td>
                    <form action="updateAdmUsr" method="post">
                        <select name="serviceStatus">
                            <option selected disabled>${userS.statusName}</option>
                            <option value="1 ${userS.serviceID}">Run</option>
                            <option value="2 ${userS.serviceID}">Stop</option>
                        </select>
                        <input type="submit" value="${bSelect}"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>