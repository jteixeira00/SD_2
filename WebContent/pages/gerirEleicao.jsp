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
<h1>Gerir Eleição</h1>

    <form action="gerireleicaoform" method="post">

        <c:forEach items="${heyBean.allEleicoes}" var="value">
            <c:out value="${value}" /><br>
        </c:forEach>

        <label><br>Escolha a eleição pretendida: <br></label>
        <%--<s:textfield name = "choice" required="true"/>--%>
        <s:select list="eleicoes" name="choice"/>
        <br><br>
        <button type = "submit">Submeter</button>

    </form>

        <form action = "adminLanding">
            <br><button>Sair</button>
        </form>

</body>
</html>
