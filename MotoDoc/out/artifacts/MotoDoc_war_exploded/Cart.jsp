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

    Cart<ProductBean> carrello = (Cart<ProductBean>) session.getAttribute("carrello");
    UserBean userBean = (UserBean) session.getAttribute("user");


    if( carrello == null){
        response.sendRedirect(response.encodeRedirectURL("./CartServlet"));
        return;
    }
%>

<% String pageTitle= "Carrello";
    request.setAttribute("pageTitle", pageTitle);
%>
<%@ include file= "_header.jsp" %>
<link rel="stylesheet" href="Style.css">

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
        <th>Nome</th>
        <th>Codice</th>
        <th>Quantità</th>
        <th>Prezzo</th>
        <th>Elimina</th>
    </tr>
    <%
        if(prodCarrello.size() > 0) {
            for(ProductBean prod: prodCarrello) {
    %>
    <tr>
        <td><%=prod.getNome()%></td>
        <th><%=prod.getCodiceProd()%></th>
        <td><input type="number" min="1" max="20"></td>
        <td><%=prod.getPrezzo()%></td>
        <td><a align="center" class= "eliminaprod" href="<%=response.encodeURL("CartServlet?action=deleteCart&id=" + prod.getCodiceProd())%>">   x   </a>
    </tr>
    <% 		}
    } else {
    %>
    <tr><td colspan="2">Il tuo carrello è vuoto</td></tr>
    <%
        }
    %>
    <a href="${pageContext.request.contextPath}/prodotti">Continua lo shopping</a>
</table>
<%if(request.getAttribute("message").equals("deleted from cart")){ // non funziona %>
    <script>alert("Il prodotto é stato cancellato correttamente")</script>
    <%}%>
