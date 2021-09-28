<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="prop"/>
<fmt:message key="button.select" var="bSelect"/>
<fmt:message key="button.login" var="bLogin"/>
<fmt:message key="button.execute" var="execute"/>
<fmt:message key="money" var="money"/>
<fmt:message key="toUserPage" var="toUserPage"/>
<fmt:message key="showAllCategory" var="showAllCategory"/>
<fmt:message key="putMoney" var="putMoney"/>
<fmt:message key="categoryNameC" var="categoryNameC"/>
<fmt:message key="IdP" var="IdP"/>
<fmt:message key="nameP" var="nameP"/>
<fmt:message key="descP" var="descP"/>
<fmt:message key="priceP" var="priceP"/>
<fmt:message key="statusS" var="statusS"/>
<fmt:message key="deleteP" var="deleteP"/>


<html>
<head>
    <title>User</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="JS/test.js"></script>
</head>
<body>
<header class="header">
    <div class="header-left">
        <h1>MyNet</h1>
    </div>
    <div class="body-centre">
        <a href="userPage" class="topic">${toUserPage}</a>
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
<p>${money} ${sessionScope.userCash}</p>
<div class="leftCentre">
    <div class="leftCentre">
        <form name="allProduct" action="findAllCategory" method="get">
             ${showAllCategory}<br>
            <input type="submit" value="${execute}">
        </form>
    </div>

    <div class="leftCentre">
        <form name="allProduct" action="addMoney" method="post">
             ${putMoney}<br>
            <label>
                <input type="number" name="cash" step="any" min="0" required>
            </label><br>
            <input type="submit" value="${execute}">
        </form>
    </div>
</div>

<table>
    <thead>
    <tr>
        <th scope="col">${categoryNameC}</th>
        <th scope="col">${IdP}</th>
        <th scope="col">${nameP}</th>
        <th scope="col">${descP}</th>
        <th scope="col">${priceP}</th>
        <th scope="col">${statusS}</th>
        <th scope="col">${deleteP}</th>
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
            <td>
                <a href="deleteService?serviceId=${userS.serviceID}&productName=${userS.productName}"
                   methods="post">
                    Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>