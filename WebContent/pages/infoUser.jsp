<%@ taglib prefix="s" uri="/struts-tags" %>
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
    <form>
        <label>Numero UC do utilizador que pretende alterar:</label>
        <s:textfield name="numerouc"/><br>

        <label>Nome:<br></label>
        <s:textfield name = "nome"/>

        <label><br>Password: *<br></label>
        <s:textfield name = "password"/>

        <label><br>Numero Cartao Cidadao:<br></label>
        <s:textfield name = "ncc"/>

        <label><br>Validade Cartao Cidadao:<br></label>
        <s:textfield name = "valcc"/>

        <label><br>Contacto Telefónico:<br></label>
        <s:textfield name = "numerotelefonico"/>

        <label><br>Morada:<br></label>
        <s:textfield name = "morada"/>

        <label><br>Departamento: *<br></label>
        <s:textfield name = "departamento" required="true"/>

        <label><br>Faculdade: <br></label>
        <s:textfield name = "faculdade" required="true"/>

    </form>


</body>
</html>
