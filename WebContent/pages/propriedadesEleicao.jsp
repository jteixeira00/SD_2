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
<h1>Propriedades Eleição</h1>

    <form action="propriedadeseleicaoform.action" method="post">


        <label>Titulo: </label>
        <c:out value="${heyBean.nomeEleicao}" /><br>
        <s:textfield name = "titulo"/>

        <label><br>Descricao: </label>
        <c:out value="${heyBean.descricaoEleicao}" /><br>
        <s:textfield name = "descricao"/>

        <label><br>Data de Inicio: </label>
        <c:out value="${heyBean.inicioEleicao}" /><br>
        <s:textfield name = "dataInicio"/>

        <label><br>Data de Fim: </label>
        <c:out value="${heyBean.fimEleicao}" /><br>
        <s:textfield name = "dataFim"/>


        <br><br>
        <button type = "submit">Submeter</button>

    </form>
</body>
</html>
