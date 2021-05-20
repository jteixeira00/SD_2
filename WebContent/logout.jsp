<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 13/05/2021
  Time: 13:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <label>Sucesso a votar</label><br>
    <form action="logout">
        <button type = "submit">Logout</button>
    </form>

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
                quote: "Votem também!",
                href: 'http://127.0.0.1:8080/Hey/eleicoesDetalhes'
            }, function(response){});
        }
    </script>

</body>
</html>
