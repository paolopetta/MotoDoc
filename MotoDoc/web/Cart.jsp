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

    Cart carrello = (Cart) session.getAttribute("carrello");
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
<style type="text/css"> @import url(Style.css);</style>

<h2>Carrello</h2>

<%
    List<ProductBean> prodCarrello = carrello.getItems();

    if(prodCarrello.size() > 0) {
%>
<button onclick="window.location.href='<%=response.encodeURL("CartServlet?action=clearCart")%>'">Clear</button>
<!--<a href="<%//=response.encodeURL("CartServlet?action=clearCart")%>">Clear</a>-->
<button onclick="window.location.href=''">Buy</button>   <!-- da inserire il link-->
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
        <th><%=prod.getNome()%></th>
        <td><%=prod.getCodiceProd()%></td>
        <td><%=prod.getQuantita()%></td>    <!-- input type="number" min="1" max="20" -->
        <td><%=prod.getPrezzo()*prod.getQuantita()%></td>
        <!--<td><a align="center" class= "eliminaprod" href="<%//=response.encodeURL("CartServlet?action=deleteCart&id=" + prod.getCodiceProd())%>">   x   </a></td>-->
        <td><button onclick="window.location.href='<%=response.encodeURL("CartServlet?action=deleteCart&id=" + prod.getCodiceProd())%>'">Elimina prod</button></td>
    </tr>
    <% 		}
    } else {
    %>
    <tr><td colspan="2">Il tuo carrello è vuoto</td></tr>
    <%
        }
    %>
    <button onclick="window.location.href='http://localhost:8080/MotoDoc_war_exploded/prodotti'">Continua lo Shopping</button>
</table>



