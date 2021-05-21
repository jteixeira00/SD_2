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

    <form action ="removerdepartamentoform" method = "post">
        <h1>Remover Departamento</h1>

        <c:forEach items="${heyBean.allDepartamentos}" var="value">
            <c:out value="${value}" /><br>
        </c:forEach>

        <!--<br><s:textfield name = "index" required="true"/>-->
        <s:select list="departamentos" name="index"/>
        <br><br>
        <button type = "submit">Submeter</button>

        <form action = "adminLanding">
            <br><br><button>Sair</button>
        </form>

</body>
</html>
