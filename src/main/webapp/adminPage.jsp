<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="prop"/>
<fmt:message key="button.select" var="bSelect"/>
<fmt:message key="messages.usernotexist" var="mesShow"/>
<fmt:message key="button.login" var="bLogin"/>
<fmt:message key="showAllUser" var="showAllUser"/>
<fmt:message key="button.execute" var="execute"/>

<html>
<head>
    <title>Admin page</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="JS/test.js"></script>
</head>
<body>
<header class="header">
    <div class="header-left">
        <h1>MyNet</h1>
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
<h1>Admin page</h1><br>
<hr>
<div class="body-centre">

    <form name="allUser" action="findAllUser" method="get">
        ----Find all User<br>
        <input type="submit" value="${showAllUser}">
    </form>

    <form name="auth" action="insertuser" method="post" onsubmit="return validateForm()">
        ----Add user by login<br>
        <input name="login" required><br>
        <input type="submit" value="${execute}">
    </form>

    <form name="auth1" action="selectuser" method="get">
        ----Select user by login<br>
        <input name="login" required><br>
        <input type="submit" value="${execute}">
    </form>
    <form name="allProduct" action="findAllCategory" method="get">
        ----Find all Category<br>
        <input type="submit" value="findAllCategory">
    </form>
    <form name="createPriceList" action="createPriceList" method="get">
        ----Create a price list<br>
        <input type="submit" value="createPriceList">
    </form>
</div>

</body>
</html>