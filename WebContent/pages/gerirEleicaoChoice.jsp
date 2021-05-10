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
    <form action="gerireleicaochoiceform.action" method="post">

        <label>Nome:<br></label>
        <c:out value="${heyBean.nomeEleicao}" /><br>
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

        <br><br>
        <button type = "submit">Submeter</button>

    </form>


</body>
</html>
