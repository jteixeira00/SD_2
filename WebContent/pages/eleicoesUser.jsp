<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 08/05/2021
  Time: 18:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Eleições que Votou:</title>
</head>
<body>

    <form action="eleicoesuserform" method="post">

    <c:forEach items="${heyBean.votosUsername}" var="value">
        <c:out value="${value}" /><br>
    </c:forEach>

    <s:text name="Eleicão Pretendida:"/><br>
    <s:textfield type ="choice" name = "choice"/>






</body>
</html>
