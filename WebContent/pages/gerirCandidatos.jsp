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
    <title>Gerir Candidatos</title>
</head>
<body>

    <h1>Gerir Candidatos</h1>

    <form action = "adicionarCandidatos">
        <button>Adicionar Candidatos</button>
    </form>



    <c:choose>
        <c:when test="${(heyBean.sizeCandidatos > 0)== true}">
            <form action = "removerCandidatos">
                <button>Remover Candidatos</button><br>
            </form>
        </c:when>
        <c:otherwise>
            <button>Remover Candidatos</button><br>
        </c:otherwise>
    </c:choose>

    <form action = "adminLanding">
        <button>Sair</button>
    </form>



</body>
</html>
