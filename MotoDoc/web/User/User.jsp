<%@ page import="it.unisa.model.UserBean" %>
<%@ page import="it.unisa.model.dao.OrdineDao" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="it.unisa.model.Ordine" %><%--
  Created by IntelliJ IDEA.
  User: pavil
  Date: 06/08/2020
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    Collection<?> ordini = (Collection<?>) request.getAttribute("ordini");
    if(ordini == null ) {
        response.sendRedirect(request.getContextPath() + "/UserServlet");
        //response.sendRedirect(response.encodeRedirectURL("./UserServlet"));
        return;
    }
%>
<html>
<head>
    <%@ include file= "../_header.jsp" %>
    <title>User</title>
</head>
<body>
<h1>I miei ordini</h1>
<!--Devo prendere la sessione e poi estrapolare gli ordini dell'user
legato a quella sessione-->
<table>
    <tr>
        <th>N. Ordine: </th>
        <th>Data Ordine</th>
        <th>N. Pezzi</th>
    </tr>
    <%
        if(ordini != null && ordini.size() > 0) {

            Iterator<?> it  = ordini.iterator();
    %>

    <%
        while(it.hasNext()) {
            Ordine ordine = (Ordine)it.next();
    %>
    <tr>
        <th><%= ordine.getCodice()%></th>
        <th><%= ordine.getDataOrd()%></th>
        <th><%= ordine.getnPezzi()%></th>
    </tr>
    <% }  %>
</table>
<% } //fine ordini  %>
</body>
</html>
