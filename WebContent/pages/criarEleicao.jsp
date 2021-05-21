<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 08/05/2021
  Time: 18:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Criar Eleição</title>
</head>
<body>

<form action ="criarEleicaoForm" method = "post">
    <h1>Criar Eleição</h1>

    <label>Titulo da Eleiçao:<br></label>
    <s:textfield name = "titulo"/>

    <label><br>Descrição:<br></label>
    <s:textfield name = "descricao"/>

    <label><br>Data de Inicio (dd-MM-yyyy):<br></label>
    <s:textfield name = "datainicio"/>

    <label><br>Hora de Inicio:<br></label>
    <s:textfield name = "horainicio"/>

    <label><br>Minuto de Inicio:<br></label>
    <s:textfield name = "minutoinicio"/>

    <label><br>Data de Fim (dd-MM-yyyy):<br></label>
    <s:textfield name = "datafim"/>

    <label><br>Hora de Fim:<br></label>
    <s:textfield name = "horafim"/>

    <label><br>Minuto de Fim:<br></label>
    <s:textfield name = "minutofim"/>

    <label><br>Restringir eleição<br> </label>
    <s:select list="tiposvoters"/>
    <br><br>
    <button type = "submit">Submeter</button>
    <input type="submit" name="exit" value="Sair" />
</form>


</body>
</html>
