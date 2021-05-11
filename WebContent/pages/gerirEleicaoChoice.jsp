<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 08/05/2021
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Consultar Votos Utilizador</title>
</head>
<body>
<h1><c:out value="${heyBean.nomeEleicao}" /></h1>

        <form action = "gerirmesas">
            <button>Gerir Mesas</button>
        </form>

        <form action = "gerirlistas">
            <button>Gerir Listas</button>
        </form>

        <form action = "gerirDepartamentos">
            <button>Gerir Departamentos</button>
        </form>

        <form action = "propriedadeseleicao">
            <button>Gerir Propriedades Eleição</button>
        </form>


</body>
</html>
