<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
	int status = response.getStatus();
%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><%=status == 404 ? "Errore 404" : status == 500 ? "Errore 500" : "Errore" %></title>
<%@ include file="/_links.jsp"%>
</head>
<body>
	<div class="content-wrapper error">
		<img src="/heaplay/images/<%=status%>.png">
		<h2>
		<%if(status == 404) { %>
			La pagina che cerchi non esiste. Ti consigliamo di tornare alla <a href="<%=response.encodeURL("/heaplay/home") %>">Home</a> :-)
		<%} else if(status == 500) { %>
			Si è verificato un errore durante l'elaborazione della tua richiesta, riprova più tardi. Nel frattempo però ritorna alla <a href="<%=response.encodeURL("/heaplay/home") %>">Home</a> :-)
		<%} else { %>
			Qualcosa è andato storto. Ritorna alla <a href="<%=response.encodeURL("/heaplay/home") %>">Home</a> :-)
		<%} %>
		</h2>
	</div>
</body>
</html>