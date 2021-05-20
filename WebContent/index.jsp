<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hey!</title>
</head>
<body>
	<s:form action="login" method="post">
		<s:text name="Username:" />
		<s:textfield name="username" /><br>
		<s:text name="Password:"/>
		<s:textfield type ="password" name = "password"/>
		<input type="submit" name="login" value="Login" />
		<p><input type="submit" name="facebook" value="Login with Facebook" /></p>
	</s:form>

	<form action = "eleicoesDetalhes">
		<button>Eleicoes Detalhes</button>
	</form>
</body>
</html>