<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Admin Console</title>
</head>
<!DOCTYPE html>
<body>
<h1>Admin Console</h1>



        <form action = "criareleicao">
            <button>Criar Eleicao</button>
        </form>

        <form action = "criarutilizador">
            <button>Criar Utilizador</button>
        </form>
        <form action = gerireleicao>
            <button>Gerir Eleicao</button>
        </form>


        <c:choose>
            <c:when test="${(heyBean.sizeEleicoesEnded > 0)== true}">
                <form action = eleicoesPassadas>
                    <button>Eleições Passadas</button>
                </form>
            </c:when>
            <c:otherwise>
                <button>Eleições Passadas</button><br>
            </c:otherwise>
        </c:choose>

            <c:choose>
                <c:when test="${(heyBean.sizePessoas > 0)== true}">
                    <form action = infouser>
                        <br> <button>Votos Utilizador</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <br><button>Votos Utilizador</button>
                </c:otherwise>
            </c:choose>

            <form action = loginFB>
                <button>Login Facebook</button>
            </form>

</body>
</html>
