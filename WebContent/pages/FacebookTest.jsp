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


<h3>Publishing Open Graph Stories</h3>


<div id="shareBtn" class="btn btn-success clearfix">Simple OG Dialog</div>



<script>
    document.getElementById('shareBtn').onclick = function() {
        FB.ui({
            display: 'popup',
            method: 'share',
            quote: "bruh moment",
            href: 'http://localhost:8080/Hey/'
        }, function(response){});
    }
</script>





</body>
</html>
