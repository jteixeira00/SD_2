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

    <h1>Gerir Mesas</h1>


    <c:choose>
        <c:when test="${(heyBean.sizeMesas > 0)== true}">
            <form action = "adicionarMesa">
                <button>Adicionar Mesas</button>
            </form>
        </c:when>
        <c:otherwise>
            <button>Adicionar Mesas</button>
        </c:otherwise>
    </c:choose>


    <c:choose>
        <c:when test="${(heyBean.sizeMesasEleicao > 0)== true}">
            <form action = "removerMesa">
                <button>Remover Mesas</button>
            </form>
        </c:when>
        <c:otherwise>
            <br><br><button>Remover Mesas</button>
        </c:otherwise>
    </c:choose>

</body>
</html>
