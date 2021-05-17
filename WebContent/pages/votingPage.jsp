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
    <title>Votar</title>
</head>

<body>
    <form action = "votarAction">


        <s:select list ="listas" name ="voto"/>
        <button type = "submit">Votar</button>
    </form>
</body>
</html>
