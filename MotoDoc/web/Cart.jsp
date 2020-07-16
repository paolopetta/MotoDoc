<%@ page import="it.unisa.model.UserBean" %>
<%@ page import="it.unisa.model.ProductBean" %>
<%@ page import="it.unisa.model.Cart" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: pavil
  Date: 14/07/2020
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = (String)request.getAttribute("error");
    UserBean user = (UserBean)session.getAttribute("user");
    Cart<ProductBean> carrello = (Cart<ProductBean>) session.getAttribute("cart");

    if( carrello == null){
        response.sendRedirect(response.encodeRedirectURL("./CartServlet"));
        return;
    }
%>
<html>
<head>
    <title>Carrello</title>
</head>
<body>

<h2>Carrello</h2>

<%
    List<ProductBean> prodCarrello = carrello.getItems();

    if(prodCarrello.size() > 0) {
%>
<a href="<%=response.encodeURL("CartServlet?action=clearCart")%>">Clear</a>
<a href="">Buy</a>
<%  } %>
<table>
    <tr>
        <th>Name</th>
        <th>Action</th>
    </tr>
    <%
        if(prodCarrello.size() > 0) {
            for(ProductBean prod: prodCarrello) {
    %>
    <tr>
        <td><%=prod.getNome()%></td>
        <td><a href="<%=response.encodeURL("CartServlet?action=deleteCart&id=" + prod.getCodiceProd())%>">Delete from cart</a>
    </tr>
    <% 		}
    } else {
    %>
    <tr><td colspan="2">Non ci sono prodotti nel carrello</td></tr>
    <%
        }
    %>
</table>

</body>
</html>