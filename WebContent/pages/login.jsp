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
    <title>Gerir Listas</title>
</head>
<body>

    <h1>Gerir Listas</h1>

    <form action = "adicionarLista">
        <button>Adicionar Lista</button>
    </form>



   <p><a href = "<c out values="${facebookBean.authURL}"/>"/>Login with Facebook</p>


</body>
</html>