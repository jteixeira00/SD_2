<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 08/05/2021
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Facebook Profile</title>
</head>
<body>

<c:choose>
    <c:when test = "${session.loggedin == true}">
        <p>Welcome <c:out value = "${HeyBean.name}"/>!!!</p>
    </c:when>
    <c:otherwise>
        <p>Welcome, anon. Login first.</p>
    </c:otherwise>
</c:choose>


</body>
</html>
