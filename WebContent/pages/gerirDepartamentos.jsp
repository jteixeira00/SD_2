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

    <h1>Gerir Departamentos</h1>

    <form action = "adicionarDepartamento">
        <button>Adicionar Departamento</button>
    </form>



    <c:choose>
        <c:when test="${(heyBean.sizeDepartamento > 0)== true}">
            <form action = "removerDepartamento">
                <button>Remover Departamentos</button>
            </form>
        </c:when>
        <c:otherwise>
            <button>Remover Departamentos</button>
        </c:otherwise>
    </c:choose>


</body>
</html>
