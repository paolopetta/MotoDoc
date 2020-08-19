<%@ page import="it.unisa.model.*" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %><%--
  Created by IntelliJ IDEA.
  User: pavil
  Date: 02/06/2020
  Time: 22:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Collection<?> offerte= (Collection<?>) request.getAttribute("offerte");
    ProductBean offerta=(ProductBean) request.getAttribute("Prodotto");
    if(offerte == null ) {
        response.sendRedirect(response.encodeRedirectURL("./home"));
        return;
    }
%>

<% String pageTitle= "Home";
    request.setAttribute("pageTitle", pageTitle);
%>
<%@ include file= "_header.jsp" %>
<title>MotoDoc</title>
<link rel="stylesheet" href="Style.css">
<body>
    <!-- Categorie -->
    <div class="container" align="center">
        <div class="row">
        <div class="col">
            <a href="/prodotti?categoria=carrozzeria" class="bottom-Cat1" align="center"><img src="https://cdn2.iconfinder.com/data/icons/elasto-motorcycle-gear-and-parts/26/motorcycle-512.png" alt="Carrozzeria" width="70" height="70" ></a>
            <h5>Carrozzeria</h5>
        </div>
        <div class="col">
            <a href="#" class="bottom-Cat2" align="center"><img src="https://i.ya-webdesign.com/images/vector-rims-motorbike-wheel-9.png" alt="Pneumatici" width="70" height="70" ></a>
            <h5>Pneumatici</h5>
        </div>
        <div class="col">
            <a href="#" class="bottom-Cat3" align="center"><img src="https://image.flaticon.com/icons/png/512/2061/2061866.png" alt="Motore" width="70" height="70" ></a>
            <h5>Motore</h5>
        </div>
    </div>

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
                    <h5>Ci prendiamo cura di te e della tua moto</h5>
                </div>
            </div>
            <div class="carousel-item">
                <img src="https://i.imgur.com/3bnXd8w.jpg" class="d-block w-100" alt="...">
                <div class="carousel-caption d-none d-md-block">
                    <h5>Cerchi un ricambio? Ti aiutiamo noi</h5>
                </div>
            </div>
            <div class="carousel-item">
                <img src="https://i.imgur.com/mepXO3E.jpg" class="d-block w-100" alt="...">
                <div class="carousel-caption d-none d-md-block">
                    <h5><span style="color:orangered";>Quattro ruote muovono il corpo, due ruote muovono l’anima.</span></h5>
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
                <a href="<%=response.encodeURL("CartServlet?action=addCart&id=" + bean.getCodiceProd())%>" class="btn btn-primary" align="center">Aggiungi al carrello</a>
            </div>
        </div>
    </div>
    <% 	} %>
</div>
<% } //fine offerte  %>

    </div>
    <%@ include file= "_footer.jsp" %>
</body>

    <script type="text/javascript">
        $(document).ready(function(){
            $(".bottom-Cat1").hover(function(){
                $(".bottom-Cat1").css({
                    "opacity": "0.5"
                });
            }, function(){
                $(this).css({
                    "opacity": "1"
                });
            });
        });
        $(document).ready(function(){
            $(".bottom-Cat2").hover(function(){
                $(".bottom-Cat2").css({
                    "opacity": "0.5"
                });
            }, function(){
                $(this).css({
                    "opacity": "1"
                });
            });
        });
        $(document).ready(function(){
            $(".bottom-Cat3").hover(function(){
                $(".bottom-Cat3").css({
                    "opacity": "0.5"
                });
            }, function(){
                $(this).css({
                    "opacity": "1"
                });
            });
        });

    </script>
