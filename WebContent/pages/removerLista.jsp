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
    <title>Gerir Eleição</title>
</head>
<body>

    <form action ="removerlistaform.action" method = "post">
        <h1>Remover Lista</h1>

        <c:choose>
        <c:when test="${(heyBean.sizeLista > 0)== true}">
        <br>
        <c:forEach items="${heyBean.allListas}" var="value">
            <c:out value="${value}" /><br>
        </c:forEach>

        <br><s:textfield name = "index"/>

        <br><br>
        <input type="submit" name="del" value="Remover" />
        <input type="submit" name="exit" value="Sair" />
        </c:when>
        <c:otherwise>
            <c:redirect url="gerirEleicaoChoice.jsp"/>
        </c:otherwise>
        </c:choose>

</body>
</html>
