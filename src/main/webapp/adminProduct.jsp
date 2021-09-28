<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="prop"/>
<fmt:message key="button.select" var="bSelect"/>
<fmt:message key="button.login" var="bLogin"/>
<fmt:message key="button.execute" var="execute"/>
<fmt:message key="toAdminPage" var="toAdminPage"/>
<fmt:message key="sortName" var="sortName"/>
<fmt:message key="sortNameRev" var="sortNameRev"/>
<fmt:message key="sortPrice" var="sortPrice"/>
<fmt:message key="sortPriceRev" var="sortPriceRev"/>
<fmt:message key="creatProduct" var="creatProduct"/>
<fmt:message key="nameP" var="nameP"/>
<fmt:message key="priceP" var="priceP"/>
<fmt:message key="descP" var="descP"/>
<fmt:message key="editP" var="editP"/>
<fmt:message key="IdP" var="IdP"/>
<fmt:message key="descP" var="descP"/>
<fmt:message key="deleteP" var="deleteP"/>
<fmt:message key="back" var="back"/>
<fmt:message key="edit" var="edit"/>
<fmt:message key="delete" var="delete"/>

<html>
<head>
    <title>Admin-Product</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="JS/test.js"></script>
</head>
<body>
<header class="header">
    <div class="header-left">
        <h1>MyNet</h1>
    </div>
    <div class="body-centre">
        <a href="adminPage.jsp" class="topic">${toAdminPage}</a>
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
        <input type="submit" value="${sortName}"/>
    </form>
    <form class="leftCentre" action="sortService" name="sortN" method="get">
        <input type="hidden" name="sortTyp" value="nameRevers"/>
        <input type="submit" value="${sortNameRev}"/>
    </form>
    <form class="leftCentre" action="sortService" name="sortN" method="get">
        <input type="hidden" name="sortTyp" value="price"/>
        <input type="submit" value="${sortPrice}"/>
    </form>
    <form class="leftCentre" action="sortService" name="sortN" method="get">
        <input type="hidden" name="sortTyp" value="priceRevers"/>
        <input type="submit" value="${sortPriceRev}" />
    </form>
</div>
<div class="leftCentre">
    <div class="leftCentre-1">
        <form name="addProduct" action="addProduct" method="post">
            <div class="topicT">${creatProduct}</div>
            ${nameP}<br>
            <input name="name" required><br>
            ${priceP}<br>
            <input name="price" type="number" step="any" min="0" required><br>
            ${descP}<br>
            <input name="des"><br>
            <input type="submit" value="${execute}">
        </form>
    </div>
    <div class="leftCentre-1">
        <form name="updateProduct" action="updateProduct" method="post">
            <div class="topicT">${editP}</div>
            ${IdP}<br>
            <input name="id" value="${param.id}" required><br>
            ${nameP}<br>
            <input name="name" value="${param.name}" required><br>
            ${priceP}<br>
            <input name="price" value="${param.price}" type="number" step="any" min="0" required><br>
            ${descP}<br>
            <input name="des" value="${param.des}"><br>
            <input type="submit" value="${execute}">
        </form>
    </div>
</div>

<table>
    <thead>
    <tr>
        <th scope="col">${IdP}</th>
        <th scope="col">${nameP}</th>
        <th scope="col">${descP}</th>
        <th scope="col">${priceP}</th>
        <th scope="col">${deleteP}</th>
        <th scope="col">${editP}</th>
    </tr>
    </thead>
    <c:forEach items="${sessionScope.productL}" var="products">
        <tr>
            <td>${products.id}</td>
            <td>${products.name}</td>
            <td>${products.description}</td>
            <td>${products.price}</td>
            <td>
                <a href="deleteProduct?idp=${products.id}&namep=${products.name}" methods="post">
                    ${delete}</a>
            </td>
            <td>
                <a href="adminProduct.jsp?id=${products.id}&name=${products.name}&des=${products.description}&price=${products.price}"
                   methods="post">
                    ${edit}</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="adminCategory.jsp">${back}</a>
</body>
</html>
