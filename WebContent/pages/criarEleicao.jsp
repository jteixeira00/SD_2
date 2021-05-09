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

<form action ="criarEleicaoForm.action" method = "post">
    <h1>Criar Eleição</h1>

    <label>Titulo da Eleiçao:<br></label>
    <s:textfield name = "titulo" required="true"/>

    <label><br>Descrição:<br></label>
    <s:textfield name = "descricao" required="true"/>

    <label><br>Data de Inicio (dd-MM-yyyy):<br></label>
    <s:textfield name = "datainicio" required="true"/>

    <label><br>Hora de Inicio:<br></label>
    <s:textfield name = "horainicio" required="true"/>

    <label><br>Minuto de Inicio:<br></label>
    <s:textfield name = "minutoinicio" required="true"/>

    <label><br>Data de Fim (dd-MM-yyyy):<br></label>
    <s:textfield name = "datafim" required="true"/>

    <label><br>Hora de Fim:<br></label>
    <s:textfield name = "horafim" required="true"/>

    <label><br>Minuto de Fim:<br></label>
    <s:textfield name = "minutofim" required="true"/>

    <label><br>Restringir eleição<br> </label>
    <label>1: Estudantes || 2: Docentes || 3: Funcionários<br></label>
    <s:textfield name = "tipovoter" required="true"/>
    <br><br>
    <button type = "submit">Submeter</button>
</form>


</body>
</html>
