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
    <title>Title</title>

</head>
<body>
<%@ include file= "_header.jsp" %>

<p><%=request.getAttribute("message")%></p>

<a class="btn btn-primary" href="login.jsp" role="button">Accedi</a>

<%@ include file="_footer.jsp" %>
</body>
</html>
