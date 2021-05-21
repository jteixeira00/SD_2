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
<form action ="criarUserForm" method = "post">
    <h1>Criar Utilizador</h1>

    <label><br>Tipo de Utilizador: *<br> </label>
    <s:select list="tiposusers" name ="tipo"/>

    <label><br>Nome: *<br></label>
    <s:textfield name = "nome"/>

    <label><br>Password: *<br></label>
    <s:textfield name = "password"/>

    <label><br>Número da Universidade: *<br></label>
    <s:textfield name = "numerouni"/>

    <label><br>Numero Cartao Cidadao:<br></label>
    <s:textfield name = "ncc"/>

    <label><br>Validade Cartao Cidadao:<br></label>
    <s:textfield name = "valcc"/>

    <label><br>Contacto Telefónico:<br></label>
    <s:textfield name = "numerotelefonico"/>

    <label><br>Morada:<br></label>
    <s:textfield name = "morada"/>

    <label><br>Departamento: *<br></label>
    <s:textfield name = "departamento"/>

    <label><br>Faculdade: <br></label>
    <s:textfield name = "faculdade" />

    <br><br>
    <button type = "submit">Submeter</button>
    <input type="submit" name="exit" value="Sair" />
</form>
</body>
</html>
