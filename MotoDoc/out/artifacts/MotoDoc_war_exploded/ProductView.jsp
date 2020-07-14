<%--
  Created by IntelliJ IDEA.
  User: pavil
  Date: 22/05/2020
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, it.unisa.model.ProductBean,it.unisa.model.Cart" %>
<%@ page import="it.unisa.model.*" %>
<%
    Collection<?> products = (Collection<?>) request.getAttribute("products");
    Collection<?> offerte= (Collection<?>) request.getAttribute("offerte");
    Collection<?> pneumatici= (Collection<?>) request.getAttribute("pneumatici");
    Collection<?> carrozzerie= (Collection<?>) request.getAttribute("carrozzerie");

    String error = (String)request.getAttribute("error");

    Cart<ProductBean> carrello = (Cart<ProductBean>) request.getAttribute("cart");

    if(products == null && error == null && carrello == null) {
        response.sendRedirect(response.encodeRedirectURL("./ProductControl"));
        return;
    }

    if(pneumatici == null && carrozzerie == null && offerte == null && error == null && carrello == null) {
        response.sendRedirect(response.encodeRedirectURL("./ProductControl"));
        return;
    }


    if( carrello == null){
        response.sendRedirect(response.encodeRedirectURL("./ProductControl"));
        return;
    }

    ProductBean product = (ProductBean) request.getAttribute("Prodotto");
    ProductBean pneumatico= (ProductBean) request.getAttribute("Pneumatici");
    ProductBean carrozzeria= (ProductBean) request.getAttribute("Carrozzeria");
    ProductBean offerta=(ProductBean) request.getAttribute("Prodotto");


%>

<!-- Paolo -->

<html>
<head>
    <meta charset="ISO-8859-1">
    <link href="./ProductStyle.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="Style.css">
    <title>MotoDoc</title>
</head>
<body>
<h1 align= "center">MotoDoc</h1>
<h6 align="center">Lo shop che ti coccola</h6>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="#">MotoDoc</a>

    <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item active">
                <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item">
                <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Inizia la ricerca" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Cerca</button>
        </form>
    </div>
</nav>

<!-- Categorie -->
<div class="container" align="center">
    <div class="row">
        <div class="col">
            <a href="#" class="bottom-Cat" align="center"><img src="https://cdn2.iconfinder.com/data/icons/elasto-motorcycle-gear-and-parts/26/motorcycle-512.png" alt="Carrozzeria" width="70" height="70" ></a>
            <h5>Carrozzeria</h5>
        </div>
        <div class="col">
            <a href="#" class="bottom-Cat" align="center"><img src="https://i.ya-webdesign.com/images/vector-rims-motorbike-wheel-9.png" alt="Pneumatici" width="70" height="70" ></a>
            <h5>Pneumatici</h5>
        </div>
        <div class="col">
            <a href="#" class="bottom-Cat" align="center"><img src="https://image.flaticon.com/icons/png/512/2061/2061866.png" alt="Motore" width="70" height="70" ></a>
            <h5>Motore</h5>
        </div>

</div>

    <img src="https://imgur.com/Gv5DUL2" alt="logo MotoDoc">

    <!--   CAROUSEL  -->
    <div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
            <li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="https://www.clicmotor.it/skin/frontend/clicmotor/default/images/banner/banner_home1.jpg" class="d-block w-100" alt="...">
                <div class="carousel-caption d-none d-md-block">
                    <h5>First slide label</h5>
                    <p>Ci prendiamo cura di te e della tua moto</p>
                </div>
            </div>
            <div class="carousel-item">
                <img src="https://i.imgur.com/3bnXd8w.jpg" class="d-block w-100" alt="...">
                <div class="carousel-caption d-none d-md-block">
                    <h5>Second slide label</h5>
                    <p></p>
                </div>
            </div>
            <div class="carousel-item">
                <img src="https://i.imgur.com/mepXO3E.jpg" class="d-block w-100" alt="...">
                <div class="carousel-caption d-none d-md-block">
                    <h5>Third slide label</h5>
                    <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur.</p>
                </div>
            </div>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleCaptions" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleCaptions" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>



<h2>Carrello</h2>
<%
    List<ProductBean> prodCarrello = carrello.getItems();

    if(prodCarrello.size() > 0) {
%>
<a href="<%=response.encodeURL("ProductControl?action=clearCart")%>">Clear</a>
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
        <td><a href="<%=response.encodeURL("ProductControl?action=deleteCart&id=" + prod.getCodiceProd())%>">Delete from cart</a>
    </tr>
    <% 		}
    } else {
    %>
    <tr><td colspan="2">Non ci sono prodotti nel carrello</td></tr>
    <%
        }
    %>
</table>



<h2>Prodotti</h2>
<%
    if(products != null && products.size() > 0) {

        Iterator<?> it  = products.iterator();
%>
<div class="row row-cols-1 row-cols-md-4">

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
                <a href="#" class="btn btn-primary" align="center">Aggiungi al carrello</a>
            </div>
        </div>
    </div>

    <% 		} %>
</div>
<% }%>


    <!-- OFFERTE-->
    <h1>Offerte</h1>
        <%
    if(offerte != null && offerte.size() > 0) {

        Iterator<?> it  = offerte.iterator();
%>
    <div class="row row-cols-1 row-cols-md-4">

        <%
            while(it.hasNext()) {
                ProductBean bean = (ProductBean)it.next();
        %>

        <div class="col mb-4">
            <div class="card" >
                <img src="<%=bean.getImg()%>" class="card-img-top" alt="..." width="500" height="300">
                <div class="card-body">
                    <h5 class="card-title" align="center"><%=bean.getNome()%></h5>
                    <p class="card-text" align="center">€ <%=bean.getPrezzo()%></p>
                    <a href="#" class="btn btn-primary" align="center">Aggiungi al carrello</a>
                </div>
            </div>
        </div>



        <% 		} %>
    </div>
        <% } //fine offerte  %>

    <!-- PNEUMATICI-->
    <h1>Pneumatici</h1>
        <%
    if(pneumatici != null && pneumatici.size() > 0) {

        Iterator<?> it  = pneumatici.iterator();
%>
    <div class="row row-cols-1 row-cols-md-4">

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
                    <a href="#" class="btn btn-primary" align="center">Aggiungi al carrello</a>
                </div>
            </div>
        </div>



        <% 		} %>
    </div>
        <% } //fine pneumatici  %>

    <!-- Carrozzeria-->
    <h1>Carrozzeria</h1>
        <%
    if(carrozzerie != null && carrozzerie.size() > 0) {

        Iterator<?> it  = carrozzerie.iterator();
%>
    <div class="row row-cols-1 row-cols-md-4">

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
                    <a href="#" class="btn btn-primary" align="center">Aggiungi al carrello</a>
                </div>
            </div>
        </div>



        <% 		} %>
    </div>
        <% } //fine pneumatici  %>

<!-- ciao -->



<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>