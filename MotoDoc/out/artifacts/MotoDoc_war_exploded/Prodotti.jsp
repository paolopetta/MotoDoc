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
    if(products == null ) {
        response.sendRedirect(response.encodeRedirectURL("./Prodotti"));
        return;
    }
    ProductBean product = (ProductBean) request.getAttribute("Prodotto");

    String categoria= request.getParameter("categoria");
%>
<html>
<head>
    <title>Prodotti</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="Style.css">
    <link href="./ProductStyle.css" rel="stylesheet" type="text/css">

</head>
<body>
<h1>Prodotti</h1>
<form action="">
    <select name="categoria" onchange="mostraProd(this.value)">
        <option value="">Seleziona una categoria:</option>
        <option value="1">Carrozzeria</option>
        <option value="2">Pneumatici</option>
        <option value="3">Motore</option>
    </select>
</form>

<h2>Prodotti</h2>
<%
    if(products != null && products.size() > 0) {       //devo mettere al posto di products quello che viene chiesto

        Iterator<?> it  = products.iterator();
%>



<script>
    function mostraProd(str){
        if(str == ""){
            //mostra tutti i prodotti

        }
        var xmlhttp= new XMLHttpRequest();
        xmlhttp.onreadystatechange= function(){
            if(this.readyState==4 && this.status==200){
                //mostro quello che chiede
                document.getElementById( )
            }
        }
        xmlhttp.open("GET", "", true);
        xmlhttp.send();
    }
</script>


<div>
    <script>
        creaTabella("<%=categoria%>"){
            //fatto con Ale
        }
    </script>
</div>

<div class="row row-cols-1 row-cols-md-4" >

    <%
        while(it.hasNext()) {
            ProductBean bean = (ProductBean)it.next();
    %>

    <div class="col mb-4">
        <div class="card" >
            <img src="<%=bean.getImg()%>" class="card-img-top" alt="..." width="500" height="300">
            <div class="card-body">
                <h5 class="card-title" align="center"><%=bean.getNome()%></h5>
                <p class="card-text" align="center"><%=bean.getPrezzo()%></p>
                <button onclick="addToCart(this)">Aggiungi al carrello</button>
                <a href="<%=response.encodeURL("ProductControl?action=addCart&id=" + bean.getCodiceProd())%>">Aggiungi al carrello</a>
                <!--<a href="#" class="btn btn-primary" align="center">Aggiungi al carrello</a> -->
            </div>
        </div>
    </div>

    <% 		} %>
</div>
<%}%>   <!--chiusura if-->



<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

</body>
</html>
