<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" %>  
<%@ page import="it.unisa.model.ProductBean" %>
<%
 	Collection<?> products = (Collection<?>) request.getAttribute("products");
 
 	String error = (String)request.getAttribute("error");
 	
 	if(products == null && error == null) {
 		response.sendRedirect(response.encodeRedirectURL("./ProductControl"));
 		return;
 	} 
 	
 	ProductBean product = (ProductBean) request.getAttribute("Prodotto");
 	
 	%>  
 
    
  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="./ProductStyle.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<title>MotoDoc</title>
</head>
<body>
	<h1 align= "center">MotoDoc</h1>
	<h6 align="center">Lo shop che ti coccola</h6>
	
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
		  		<img src="<%=bean.getImg()%>" class="card-img-top" alt="...">
			  	<div class="card-body">
				    <h5 class="card-title" align="center"><%=bean.getNome()%></h5>
				    <p class="card-text" align="center"><%=bean.getPrezzo()%></p>
				    <a href="#" class="btn btn-primary">Aggiungi al carrello</a>
				</div>
			</div>
		</div>
			
			
	<% 		} %>
	</div>
	 	<% }%>
	

 

 
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>