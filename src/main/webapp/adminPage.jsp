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
<fmt:message key="adminPage" var="adminPage"/>
<fmt:message key="adminAddUser" var="adminAddUser"/>
<fmt:message key="selectUser" var="selectUser"/>
<fmt:message key="showAllCategory" var="showAllCategory"/>
<fmt:message key="createPriceList" var="createPriceList"/>
<fmt:message key="downloadLogInfo" var="downloadLogInfo"/>
<fmt:message key="downloadLogErr" var="downloadLogErr"/>
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
<h1>${adminPage}</h1><br>
<hr>
<div class="body-centre">
    <form class="body-centre" name="allUser" action="findAllUserLimit" method="get">
        ${showAllUser}<br>
        <input type="submit" value="${execute}">
    </form>
    <div class="body-centre">
        <form name="auth" action="insertuser" method="post" onsubmit="return validateForm()">
            ${adminAddUser}<br>
            <input name="login" required><br>
            <input class="body-centreForm" type="submit" value="${execute}">
        </form>
    </div>
    <div class="body-centre">
        <form name="auth1" action="selectuser" method="get">
            ${selectUser}<br>
            <input class="body-centreForm" name="login" required>
            <input class="body-centreForm" type="submit" value="${execute}">
        </form>
    </div>
    <div class="body-centre">
        <form name="allProduct" action="findAllCategory" method="get">
            ${showAllCategory}<br>
            <input class="body-centreForm" type="submit" value="${execute}">
        </form>
    </div>
    <div class="body-centre">
        <form name="createPriceList" action="createPriceList" method="get">
            ${createPriceList}<br>
            <input class="body-centreForm" type="submit" value="${execute}">
        </form>
    </div>
    <div class="leftCentreAdmin">
        <form class="leftCentreAdmin" action="download">
            <input type="hidden" name="download" value="logInfo"/>
            <input type="submit" value="${downloadLogInfo}">
        </form>
        <form class="leftCentreAdmin" action="download">
            <input type="hidden" name="download" value="logErr"/>
            <input type="submit" value="${downloadLogErr}">
        </form>
    </div>
</div>
</body>
</html>