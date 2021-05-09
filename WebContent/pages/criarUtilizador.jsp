<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 08/05/2021
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Criar Utilizador</title>

</head>
<body>
<form action ="criarUserForm.action" method = "post">
    <h1>Criar Utilizador</h1>

    <label>Nome: *<br></label>
    <s:textfield name = "nome" required="true"/>

    <label><br>Password: *<br></label>
    <s:textfield name = "password" required="true"/>

    <label><br>Número da Universidade: *<br></label>
    <s:textfield name = "numerouni" required="true"/>

    <label><br>Numero Cartao Cidadao:<br></label>
    <s:textfield name = "ncc" required="false"/>

    <label><br>Validade Cartao Cidadao:<br></label>
    <s:textfield name = "valcc" required="false"/>

    <label><br>Contacto Telefónico:<br></label>
    <s:textfield name = "numerotelefonico" required="false"/>

    <label><br>Morada:<br></label>
    <s:textfield name = "morada" required="false"/>

    <label><br>Departamento: *<br></label>
    <s:textfield name = "departamento" required="true"/>

    <label><br>Faculdade: <br></label>
    <s:textfield name = "faculdade" required="true"/>

    <br><br>
    <button type = "submit">Submeter</button>
</form>
</body>
</html>
