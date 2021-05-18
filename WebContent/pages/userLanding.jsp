<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Admin Console</title>
</head>
<!DOCTYPE html>
<body>
<h1>Bem-vindo <c:out value = "${HeyBean.rmiUserNome}"/></h1>



        <form action = "escolherEleicao">
            <button>Votar</button>
        </form>

        <p><a href="<c:out value="${HeyBean.authURL}"/>" /> Associate Facebook</p>

</body>
</html>
