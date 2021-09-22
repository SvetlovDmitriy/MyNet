<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="prop"/>
<fmt:message key="button.select" var="bSelect"/>
<fmt:message key="button.login" var="bLogin"/>
<fmt:message key="button.execute" var="execute"/>
<html>
<head>
    <title>User Ptoduct</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="JS/test.js"></script>
</head>
<body>
<header class="header">
    <div class="header-left">
        <h1>MyNet</h1>
    </div>
    <div class="body-centre">
        <a href="userPage.jsp" class="topic">To User Page</a>
    </div>
    <form class = "header-right" action="lang" method="get" id="f1"></form>
    <select name="lang" form="f1">
        <option>${sessionScope.lang}</option>
        <option value="en">en</option>
        <option value="ru">ru</option>
    </select>
    <input type="submit" value="${bSelect}" form="f1"/>
</header>
<h1>${sessionScope.categoryName}</h1>
<div class="leftCentre">
    <form class="leftCentre" action="sortService" name="sortN" method="get">
        <input type="hidden" name="sortTyp" value="name"/>
        <input type="submit" value="sort by name"/>
    </form>
    <form class="leftCentre" action="sortService" name="sortN" method="get">
        <input type="hidden" name="sortTyp" value="nameRevers"/>
        <input type="submit" value="sort by name revers order"/>
    </form>
    <form class="leftCentre" action="sortService" name="sortN" method="get">
        <input type="hidden" name="sortTyp" value="price"/>
        <input type="submit" value="sort by price"/>
    </form>
    <form class="leftCentre" action="sortService" name="sortN" method="get">
        <input type="hidden" name="sortTyp" value="priceRevers"/>
        <input type="submit" value="sort by price revers order" />
    </form>
</div>
<table>
    <caption>Services</caption>
    <thead>
    <tr>
        <th scope="col">Service id</th>
        <th scope="col">Service name</th>
        <th scope="col">Service description</th>
        <th scope="col">Service price</th>
        <th scope="col">Delete service</th>
    </tr>
    </thead>
    <c:forEach items="${sessionScope.productL}" var="products">
        <tr>
            <td>${products.id}</td>
            <td>${products.name}</td>
            <td>${products.description}</td>
            <td>${products.price}</td>
            <td>
                <a href="addService?idp=${products.id}&namep=${products.name}" methods="post">
                    Add</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>