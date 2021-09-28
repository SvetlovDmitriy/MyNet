<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/taglib/mytaglib.tld" prefix="mytag"%>
<% request.setCharacterEncoding("UTF-8"); %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="prop"/>
<fmt:message key="button.select" var="bSelect"/>
<fmt:message key="messages.usernotexist" var="mesShow"/>
<fmt:message key="button.login" var="bLogin"/>
<fmt:message key="toAdminPage" var="toAdminPage"/>
<fmt:message key="by" var="by"/>
<fmt:message key="previous" var="previous"/>
<fmt:message key="next" var="next"/>

<html>
<head>
    <title>View all user</title>
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
<div class="pagination">
    <a href="findAllUserLimit?recordsPerPage=2">${by} 2</a>
    <a href="findAllUserLimit?recordsPerPage=3">${by} 3</a>
</div>
<br><br>
<mytag:viewUser userL="${sessionScope.userL}"/>
<div class="pagination">
<c:if test="${sessionScope.currentPage != 1}">
    <a href="findAllUserLimit?page=${sessionScope.currentPage - 1}">${previous}</a>
</c:if>

    <c:forEach begin="1" end="${sessionScope.noOfPages}" var="i">
        <c:choose>
            <c:when test="${sessionScope.currentPage eq i}">
                <a href="#"><div class="topicT">${i}</div></a>
            </c:when>
            <c:otherwise>
                <a href="findAllUserLimit?page=${i}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

<c:if test="${sessionScope.currentPage lt sessionScope.noOfPages}">
    <a href="findAllUserLimit?page=${sessionScope.currentPage + 1}">${next}</a>
</c:if>
</div>

</body>
</html>