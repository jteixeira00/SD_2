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

        <br><br>
        <button type = "submit">Continuar</button>

</body>
</html>
