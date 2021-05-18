<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Admin Console</title>
    <script type="text/javascript">

        var websocket = null;

        window.onload = function() { // URI = ws://10.16.0.165:8080/WebSocket/ws
            connect('ws://' + 'localhost:8081' + '/WebSocket/ws');
            document.getElementById("chat").focus();
        }

        function connect(host) { // connect to the host websocket
            if ('WebSocket' in window)
                websocket = new WebSocket(host);
            else if ('MozWebSocket' in window)
                websocket = new MozWebSocket(host);
            else {
                writeToHistory('Get a real browser which supports WebSocket.');
                return;
            }

            websocket.onopen    = onOpen; // set the 4 event listeners below
            websocket.onclose   = onClose;
            websocket.onmessage = onMessage;
            websocket.onerror   = onError;
        }

        function onOpen(event) {
            writeToHistory('Connected to ' + window.location.host + '.');
            document.getElementById('chat').onkeydown = function(key) {
                if (key.keyCode == 13)
                    doSend(); // call doSend() on enter key press
            };
        }

        function onClose(event) {
            writeToHistory('WebSocket closed (code ' + event.code + ').');
            document.getElementById('chat').onkeydown = null;
        }

        function onMessage(message) { // print the received message
            writeToHistory(message.data);
        }

        function onError(event) {
            writeToHistory('WebSocket error.');
            document.getElementById('chat').onkeydown = null;
        }

        function doSend() {
            var message = document.getElementById('chat').value;
            if (message != '')
                websocket.send(message); // send the message to the server
            document.getElementById('chat').value = '';
        }

        function writeToHistory(text) {
            var history = document.getElementById('history');
            var line = document.createElement('p');
            line.style.wordWrap = 'break-word';
            line.innerHTML = text;
            history.appendChild(line);
            history.scrollTop = history.scrollHeight;
        }

    </script>
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

<div>
    <div id="container"><div id="history"></div></div>
    <p><input type="text" placeholder="type to chat" id="chat"></p>
</div>
</body>
</html>
