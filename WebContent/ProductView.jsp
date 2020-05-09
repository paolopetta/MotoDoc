<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,it.unisa.model.ProductBean"%>
<%
 	Collection<?> products = (Collection<?>) request.getAttribute("products");
 
 	String error = (String)request.getAttribute("error");
 	
 	if(products == null && error == null) {
 		response.sendRedirect(response.encodeRedirectURL("./ProductControl"));
 		return;
 	}%>  
    
  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>MotoDoc</title>
</head>
<body>
	<h1>MotoDoc</h1>
	
	
	
</body>
</html>