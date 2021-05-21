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



    <c:choose>
        <c:when test="${(heyBean.sizeLista > 0)== true}">
            <form action = "removerLista">
                <button>Remover Lista</button>
            </form>
        </c:when>
        <c:otherwise>
            <button>Remover Lista</button>
        </c:otherwise>
    </c:choose>


    <c:choose>
        <c:when test="${(heyBean.sizeLista > 0)== true}">
            <form action = "escolherLista">
                <button>Gerir Candidatos</button>
            </form>
        </c:when>
        <c:otherwise>
            <button><br>Gerir Candidatos</button>
        </c:otherwise>
    </c:choose>

    <form action = "adminLanding">
        <button>Sair</button>
    </form>


</body>
</html>
