<%@ page import="it.unisa.model.UserBean" %><%--
  Created by IntelliJ IDEA.
  User: pavil
  Date: 02/06/2020
  Time: 09:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String jspPath = (String) request.getAttribute("jspPath");
    String pageTitle = (String) request.getAttribute("pageTitle");
    UserBean user= (UserBean) session.getAttribute("user");
%>

<html>
<head>
    <title><%=pageTitle%></title>
</head>
<body>

    <jsp:include page="/_header.jsp" />
    <div class="content-wrapper">
        <div class="search-content">
            <div class="loading hidden">
                <img alt="Loading..." src="/heaplay/images/loading.gif" width="50px">
            </div>
            <div id="content">
                <div class="flex-container">
                </div>
            </div>
        </div>
    <jsp:include page="<%=jspPath%>"/>
    </div>
    <jsp:include page="/_footer.jsp" />
</body>
</html>
