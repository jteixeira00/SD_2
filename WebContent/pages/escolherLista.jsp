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
    <title>Escolher Lista</title>
</head>
<body>
<h1>Escolher Lista</h1>

    <form action="escolherlistaform" method="post">
        <label><br>Escolha a lista pretendida: <br></label>
        <s:select list="listas" name="choice"/>
        <br><br>
        <button type = "submit">Submeter</button>

    </form>
</body>
</html>
