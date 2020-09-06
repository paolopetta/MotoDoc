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

<h2 align="center" style="color: orangered">Carrello</h2>

<%
    List<ProductBean> prodCarrello = carrello.getItems();
%>


<table class="table">
    <thead class="thead-light">
    <tr>
        <th scope="col">Nome</th>
        <th scope="col">Codice</th>
        <th scope="col">Quantità</th>
        <th scope="col">Prezzo</th>
        <th scope="col">Elimina</th>
    </tr>
    </thead>

    <%
        if(prodCarrello.size() > 0) {
            for(ProductBean prod: prodCarrello) {
    %>
    <tr>
        <th><%=prod.getNome()%></th>
        <td><%=prod.getCodiceProd()%></td>
        <td><%=prod.getQuantita()%></td>    <!-- input type="number" min="1" max="20" -->
        <td><%=prod.getPrezzo()*prod.getQuantita()+"&#8364"%></td>
        <!--<td><a align="center" class= "eliminaprod" href="<%//=response.encodeURL("CartServlet?action=deleteCart&id=" + prod.getCodiceProd())%>">   x   </a></td>-->
        <td><button onclick="window.location.href='<%=response.encodeURL("CartServlet?action=deleteCart&id=" + prod.getCodiceProd())%>'" class="btn btn-outline-danger btn-sm">x</button></td>
    </tr>
    <% 		}
    } else {
    %>
    <tr><td colspan="2">Il tuo carrello è vuoto</td></tr>

    <%
        }
    %>
    <!--<button onclick="window.location.href='http://localhost:8080/MotoDoc_war_exploded/prodotti'">Continua lo Shopping</button>-->
</table>

<button onclick="window.location.href='http://localhost:8080/MotoDoc_war_exploded/prodotti'" class="btn btn-secondary btn-sm" >Continua lo Shopping</button>
<%
if(prodCarrello.size() > 0) {
%>

<button onclick="window.location.href='<%=response.encodeURL("CartServlet?action=clearCart")%>'" class="btn btn-secondary btn-sm">Clear</button>
<!--<a href="<%//=response.encodeURL("CartServlet?action=clearCart")%>">Clear</a>-->
<button onclick="window.location.href=''" class="btn btn-secondary btn-sm">Buy</button>   <!-- da inserire il link-->
<%  } %>

<%@ include file= "_footer.jsp" %>





