<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="prop"/>
<fmt:message key="button.select" var="bSelect"/>
<fmt:message key="messages.usernotexist" var="mesShow"/>
<fmt:message key="button.login" var="bLogin"/>

<html>
<head>
    <title>View all user</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="JS/test.js"></script>
</head>
<body>
<header class="header">
    <div class="header-left">
        <h1>My Net</h1>
    </div>
    <div class="body-centre">
        <a href="adminPage.jsp" class="topic">To Admin Page</a>
    </div>
    <form class="header-right" action="lang" method="get" id="f1"></form>
    <label>
        <select name="lang" form="f1">
            <option>${sessionScope.lang}</option>
            <option value="en">en</option>
            <option value="ru">ru</option>
        </select>
    </label>
    <input type="submit" value="${bSelect}" form="f1">
</header>
<h1><fmt:message key="viewalluser"/></h1>
<table>
    <caption>Users List</caption>
    <thead>
    <tr>
        <th scope="col">User id</th>
        <th scope="col">User login</th>
        <th scope="col">User cash</th>
    </tr>
    </thead>
    <c:forEach items="${sessionScope.userL}" var="user">
        <tr>
            <td>${user.id}</td>
            <td><a href="selectuser?login=${user.login}">${user.login}</a></td>
            <td>${user.cash}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

