<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="prop"/>
<fmt:message key="button.select" var="bSelect"/>
<fmt:message key="button.login" var="bLogin"/>

<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="JS/test.js"></script>
</head>
<body>
<header class="header">
    <div class="header-left">
        <h1>MyNet</h1>
    </div>
    <form class = "header-right" action="lang" method="get" id="f1"></form>
    <label>
        <select name="lang" form="f1">
            <option>${sessionScope.lang}</option>
            <option value="en">en</option>
            <option value="ru">ru</option>
        </select>
    </label>
    <input type="submit" value="${bSelect}" form="f1">
</header>
<div class="body-centre">
    <p><fmt:message key="${content}"/></p>
</div>
</body>
</html>