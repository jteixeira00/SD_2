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

    <form action ="adicionarlistaform.action" method = "post">
        <h1>Adiconar Lista</h1>

        <label>Nome da Lista: <br></label>
        <s:textfield name = "nome" required="true"/><br>

        <label><br>Adicionar Candidatos: <br></label>
        <c:forEach items="${heyBean.allPessoas}" var="value">
            <c:out value="${value}" /><br>
        </c:forEach>

        <s:textfield name = "choice" required="true"/>

        <br><br>
        <button type = "submit">Submeter</button>

</body>
</html>
