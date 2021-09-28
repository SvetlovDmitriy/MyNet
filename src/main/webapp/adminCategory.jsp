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
<fmt:message key="categoryC" var="categoryC"/>
<fmt:message key="addCategory" var="addCategory"/>
<fmt:message key="categoryIDC" var="categoryIDC"/>
<fmt:message key="categoryNameC" var="categoryNameC"/>
<fmt:message key="delCategoryC" var="delCategoryC"/>

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
<h1>${categoryC}</h1>
<div class="body-centre">
    <form name="addcategory" action="addCategory" method="post">
        ${addCategory}<br>
        <input class="body-centreForm" name="categoryName" required>
        <input class="body-centreForm" type="submit" value="${execute}">
    </form>
</div>
<table>
    <thead>
    <tr>
        <th scope="col">${categoryIDC}</th>
        <th scope="col">${categoryNameC}</th>
        <th scope="col">${delCategoryC}</th>
    </tr>
    </thead>
    <c:forEach items="${sessionScope.categoryL}" var="category">
        <tr>
            <td>
                ${category.id}
            </td>
            <td><a href="findAllProduct?categoryId=${category.id}&categoryName=${category.name}">
                    ${category.name}</a>
            </td>
            <td>
                <a href="deleteCategory?id=${category.id}&name=${category.name}" methods="post">
                        Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
