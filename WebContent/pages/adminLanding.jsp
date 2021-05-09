<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 08/05/2021
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Console</title>
</head>
<!DOCTYPE html>
<body>
  <form>
    <h1>Admin Console</h1>
    <div>
        <form action = "criareleicao">
            <button>Criar Eleição</button>
        </form>
        <form action = "criarutilizador">
            <button>Criar Utilizador</button>
        </form>
        <form action = gerireleicao>
            <button>Gerir Eleicao</button>
        </form>
        <form action = eleicoesPassadas>
            <button>Eleições Passadas</button>
        </form>
        <form action = infouser>
            <button>Votos Utilizador</button>
        </form>
    </div>
  </form>
</body>
</html>
