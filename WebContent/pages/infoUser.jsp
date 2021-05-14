<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 08/05/2021
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Consultar Votos Utilizador</title>
</head>
<body>

        <form action ="votosuserform" method = "post">
            <h1>Votos Utilizador</h1>

            <c:forEach items="${heyBean.allPessoas}" var="value">
                <c:out value="${value}" /><br>
            </c:forEach>

            <s:textfield name = "choice" />

            <br><br>
            <input type="submit" name="select" value="Selecionar" />
            <input type="submit" name="exit" value="Sair" />
            <br>

            <c:choose>
                <c:when test="${(heyBean.choiceUser != -1)== true}">
                    <c:forEach items="${heyBean.votosUser}" var="value">
                        <c:out value="${value}" /><br>
                    </c:forEach>
                </c:when>
            </c:choose>





</body>
</html>
