<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

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

        <c:choose>
            <c:when test="${(HeyBean.votosSize)== true}">
                <form action = "share">
                    <button>Partilhar Resultados</button>
                </form>
            </c:when>
            <c:otherwise>
                <br><button>Partilhar Resultados</button>
            </c:otherwise>
        </c:choose>

        <form action = "logout">
            <button>Sair</button>
        </form>



</body>
</html>
