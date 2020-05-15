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
	<h2>Products</h2>

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
		  		<img src="https://source.unsplash.com/600x500/?motocycle" class="card-img-top" alt="...">
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