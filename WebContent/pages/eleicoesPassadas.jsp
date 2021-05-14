<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 08/05/2021
  Time: 18:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Eleições Passadas</title>
</head>
<body>

    <c:forEach items="${heyBean.eleicoesPassadas}" var="value">
        <c:out value="${value}" /><br>
    </c:forEach>

    <form action = adminLanding>
        <button>Exit</button>
    </form>



</body>
</html>
