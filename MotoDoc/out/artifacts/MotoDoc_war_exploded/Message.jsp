<%--
  Created by IntelliJ IDEA.
  User: pavil
  Date: 29/07/2020
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Messaggio di registrazione</title>
    <link rel="stylesheet" href="Style.css">

</head>
<body>
<%@ include file= "_header.jsp" %>

<p id="message"><%=request.getAttribute("message")%></p>

<div id="tasto">
    <a class="btn btn-secondary btn-sm" href="login.jsp" role="button">Accedi</a>
</div>

<%@ include file="_footer.jsp" %>
</body>
</html>
