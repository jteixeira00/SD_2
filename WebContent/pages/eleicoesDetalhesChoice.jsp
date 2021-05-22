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
    <title>Eleições que Votou:</title>
</head>
<body>

    <c:forEach items="${heyBean.detalhesEleicaoChoice}" var="value">
        <c:out value="${value}" /><br>
    </c:forEach>

    <br>
    <script>
        window.fbAsyncInit = function() {
            FB.init({
                appId            : '182920250372527',
                autoLogAppEvents : true,
                xfbml            : true,
                version          : 'v10.0'
            });
        };
    </script>

    <script async defer crossorigin="anonymous" src="https://connect.facebook.net/en_US/sdk.js"></script>



    <form id = "shareBtn" action="logout">
        <button>Share Facebook</button>
    </form>


    <script>
        document.getElementById('shareBtn').onclick = function() {
            FB.ui({
                display: 'popup',
                method: 'share',
                quote: "${heyBean.detalhesEleicaoChoiceSimple}",
                href: 'http://127.0.0.1:8080/Hey/eleicoesDetalhes'
            }, function(response){});
        }
    </script>

    <form action = "logout">
        <br><br><button>Sair</button>
    </form>






</body>
</html>
