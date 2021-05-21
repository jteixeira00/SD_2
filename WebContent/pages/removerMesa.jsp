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
    <title>Remover Mesa</title>
</head>
<body>

    <form action ="removermesaform" method = "post">
        <h1>Remover Mesa</h1>

        <c:forEach items="${heyBean.allMesasEleicao}" var="value">
            <c:out value="${value}" /><br>
        </c:forEach>

        <s:textfield name = "choice" required="true"/>
        <br><br>
        <button type = "submit">Submeter</button>

        <form action = "adminLanding">
            <br><button>Sair</button>
        </form>

</body>
</html>
