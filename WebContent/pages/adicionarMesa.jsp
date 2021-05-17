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
    <title>Adicionar Mesa</title>
</head>
<body>

<form action ="adicionarmesaform" method = "post">
    <h1>Adicionar Mesa</h1>

    <c:forEach items="${heyBean.allMesas}" var="value">
        <c:out value="${value}" /><br>
    </c:forEach>

    <s:textfield name = "choice"/>

    <br><br>
    <input type="submit" name="add" value="Adicionar" />
    <input type="submit" name="exit" value="Sair" />
</body>
</html>
