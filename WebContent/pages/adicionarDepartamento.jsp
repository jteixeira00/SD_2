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

    <form action ="adicionardepartamentoform.action" method = "post">
        <h1>Adiconar Departamento</h1>

        <label>Nome do Departamento: <br></label>
        <s:textfield name = "nome" required="true"/>
        <br><br>
        <button type = "submit">Submeter</button>

</body>
</html>
