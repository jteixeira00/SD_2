<%@ taglib prefix="s" uri="/struts-tags" %>
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

    <form action="gerirleleicaoform.action" method="post">
        
        <br><c:out value="${heyBean.allEleicoes}" /><br>
        <s:text name="Escolha a eleição pretendida:" />
        <s:textarea value = "%res"/>




    </form>
</body>
</html>
