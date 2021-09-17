<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="prop"/>
<fmt:message key="button.select" var="bSelect"/>
<fmt:message key="button.login" var="bLogin"/>
<fmt:message key="login.len" var="loginl"/>
<fmt:message key="password.len" var="passwordl"/>
<html>
<head>
    <title>Start</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="JS/test.js"></script>
</head>
<body>
<header class="header">
    <div class="header-left">
        <h1>MyNet</h1>
    </div>
    <form class = "header-right" action="lang" method="get" id="f1"></form>
    <select name="lang" form="f1">
        <option>${sessionScope.lang}</option>
        <option value="en">en</option>
        <option value="ru">ru</option>
    </select>
    <input type="submit" value="${bSelect}" form="f1">
</header>
<div class="body-centre">
    <h2><fmt:message key="start.welcome"/></h2>
</div>
<form class="body-centre" name="auth" action="login" method="post" onsubmit="return validateForm()">
    ${loginl}
    <input name="login"><br>
    ${passwordl}
    <input name="password" type="password"><br>
    <input type="submit" value="${bLogin}">
</form>
</body>
</html>
