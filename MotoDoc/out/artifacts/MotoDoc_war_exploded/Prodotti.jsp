<%@ page import="java.util.Collection" %>
<%@ page import="it.unisa.model.ProductBean" %>
<%@ page import="java.util.Iterator" %><%--
  Created by IntelliJ IDEA.
  User: pavil
  Date: 29/06/2020
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Collection<?> products = (Collection<?>) request.getAttribute("products");
    /*if(products == null ) {
        response.sendRedirect(response.encodeRedirectURL("./Prodotti"));
        return;
    }*/
    ProductBean product = (ProductBean) request.getAttribute("Prodotto");

    //String categoria= request.getParameter("categoria");
%>

<% String pageTitle= "Home";
    request.setAttribute("pageTitle", pageTitle);
%>
<%@ include file= "_header.jsp" %>
<link rel="stylesheet" href="Style.css">

    <title>Prodotti</title>

    <script type="text/javascript" src="prodotti.js"></script>
    <script src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="Style.css">
    <link href="./ProductStyle.css" rel="stylesheet" type="text/css">


<h1>Prodotti</h1>
<form action="">
    <select name="categoria" onchange="showProd(this.value,'${pageContext.request.contextPath}')">
        <!--ho dovuto inserire un altra voce perché l'evento é onchange-->
        <option value="#">Seleziona una categoria:</option>
        <option value="tutti">Tutti</option>
        <option value="carrozzeria">Carrozzeria</option>
        <option value="pneumatici">Pneumatici</option>
        <option value="meccanica">Meccanica</option>
    </select>
</form>

<h2>Prodotti</h2>

<div id="showProd">
    <script>

    </script>
</div>


<script type="text/javascript" src="Img.js"></script>

<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>












<%
    /* if(products != null && products.size() > 0) {       //devo mettere al posto di products quello che viene chiesto

         Iterator<?> it  = products.iterator();*/
%>
<!--
<div class="row row-cols-1 row-cols-md-4" >

<%
    /* while(it.hasNext()) {
         ProductBean bean = (ProductBean)it.next();*/
%>

<div class="col mb-4">
<div class="card">
<img src="<%//=bean.getImg()%>" class="card-img-top" alt="..." width="500" height="300">
<div class="card-body">
<h5 class="card-title" align="center"><%/*=bean.getNome()*/%></h5>
<p class="card-text" align="center"><%/*=bean.getPrezzo()*/%></p>
<a href="<%/*=response.encodeURL("CartServlet?action=addCart&id=" + bean.getCodiceProd())*/%>" class="btn btn-primary" align="center">Aggiungi al carrello</a>
</div>
</div>
</div>

<% 	//	} %>
</div> -->
<%//}%>   <!--chiusura if-->
