<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 13/05/2021
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Escolha Eleição</title>

</head>
<body>
<form action="votingPageAction">
  <label>Eleição em que pretende votar:<br></label>
  <s:select list = "eleicoes" name = "choice"/>
  <button type="submit">Submeter</button>
</form>

</body>
</html>
