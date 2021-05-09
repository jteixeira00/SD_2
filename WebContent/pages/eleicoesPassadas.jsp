<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 08/05/2021
  Time: 18:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Eleições Passadas</title>
</head>
<body>
    <form action="eleicoespassadasform" method ="post">
        <s:textarea value = "%res"/>
    </form>
    <form action ="alterarEleicaoForm" method = "post">
        <label>ELeição que pretende alterar:</label>
        <s:textfield name = "choice" required = "true"/>

        <label>Titulo da Eleiçao:<br></label>
        <s:textfield name = "titulo" required="false"/>

        <label><br>Descrição:<br></label>
        <s:textfield name = "descricao" required="false"/>

        <label><br>Data de Inicio (dd-MM-yyyy):<br></label>
        <s:textfield name = "datainicio" required="false"/>

        <label><br>Hora de Inicio:<br></label>
        <s:textfield name = "horainicio" required="false"/>

        <label><br>Minuto de Inicio:<br></label>
        <s:textfield name = "minutoinicio" required="false"/>

        <label><br>Data de Fim (dd-MM-yyyy):<br></label>
        <s:textfield name = "datainicio" required="false"/>

        <label><br>Hora de Fim:<br></label>
        <s:textfield name = "horafim" required="false"/>

        <label><br>Minuto de Fim:<br></label>
        <s:textfield name = "minutofim" required="false"/>

        <label><br>Restringir eleição<br> </label>
        <label>1: Estudantes || 2: Docentes || 3: Funcionários<br></label>
        <s:textfield name = "tipovoter" required="false"/>
        <br><br>
        <button type = "submit">Submeter</button>
    </form>
</body>
</html>
