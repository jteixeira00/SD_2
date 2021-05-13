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
    <title>Adicionar Lista</title>
</head>
<body>

    <form action ="adicionarcandidatosform.action" method = "post">
        <h1>Adicionar Lista</h1>

        <label><br>Adicionar Candidatos: <br></label><br>
        <c:forEach items="${heyBean.allPessoas}" var="value">
            <c:out value="${value}" /><br>
        </c:forEach>

        <br><s:textfield name = "choice"/>

        <br><br>
        <input type="submit" name="add" value="Adicionar" />
        <input type="submit" name="exit" value="Sair" />
</body>
</html>
