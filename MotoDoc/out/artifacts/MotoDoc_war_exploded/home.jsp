<%@ page import="it.unisa.model.UserBean" %><%--
  Created by IntelliJ IDEA.
  User: pavil
  Date: 02/06/2020
  Time: 22:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UserBean user = (UserBean)session.getAttribute("user");
%>
<h2>Homepage</h2>
<hr class= "hr-form">