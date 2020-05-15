<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table border="1">
	<tr>
		<th>Codice Prodotto <a href="<%=response.encodeURL("ProductControl?sort=codiceProd")%>">Sort</a></th>
		<th>Nome <a href="<%=response.encodeURL("ProductControl?sort=nome")%>">Sort</a></th>
		<th>Descrizione <a href="<%=response.encodeURL("ProductControl?sort=descrizione")%>">Sort</a></th>
		<th>Prezzo <a href="<%=response.encodeURL("ProductControl?sort=prezzo")%>">Sort</a></th>
		<th>Marca <a href="<%=response.encodeURL("ProductControl?sort=marca")%>">Sort</a></th>
		<th>Disponibilità <a href="<%=response.encodeURL("ProductControl?sort=disponibilità")%>">Sort</a></th>
		<th>Action</th>
	</tr>
		<tr>
				<td align= "center"><%=bean.getCodiceProd()%></td>
				<td align= "center"><%=bean.getNome()%></td>
				<td><%=bean.getDescrizione()%></td>
				<td align= "center"><%=bean.getPrezzo()%></td>
				<td align= "center"><%=bean.getMarca()%></td>
				<td align= "center"><%=bean.getDisponibilità()%></td>
				
				<td>
					<a href="<%=response.encodeURL("ProductControl?action=details&id=" + bean.getCodiceProd())%>">Dettagli</a>
					<a href="<%=response.encodeURL("ProductControl?action=delete&id=" + bean.getCodiceProd())%>">Elimina</a>
					<a href="<%=response.encodeURL("ProductControl?action=addCart&id=" + bean.getCodiceProd())%>">Aggiungi al Carrello</a>
				</td>
			</tr>
	<%
		if(product != null && !product.isEmpty()) {
	%>
		<h2>Deatils</h2>
		<table>
			<tr>
				<th>Codice Prodotto</th>
				<th>Nome</th>
				<th>Descrizione</th>
				<th>Prezzo</th>
				<th>Marca</th>
				<th>Disponibilità</th>
			</tr>
			<tr>
				<td><%=product.getCodiceProd()%></td>
				<td><%=product.getNome()%></td>
				<td><%=product.getDescrizione()%></td>
				<td><%=product.getPrezzo()%></td>
				<td><%=product.getMarca()%></td>
				<td><%=product.getDisponibilità()%></td>
				
			</tr>
		</table>
	
			<form action="<%=response.encodeURL("ProductControl")%>" method="POST">
			<fieldset>
				<legend><b>Update</b></legend>
				<input type="hidden" name="action" value="update">
				<input type="hidden" name="id" value="<%=product.getCodiceProd()%>">
				
				<label for="nome">Nome:</label><br>
				<input name="nome" type="text" maxlength="20" placeholder="Inserisci nome" required value="<%=product.getNome()%>"><br>
				
				<label for="descrizione">Descrizione:</label><br>
				<textarea name="descrizione" maxlength="100" rows="3" placeholder="Inserisci descrizione" required><%=product.getDescrizione()%></textarea><br>
				
				<label for="prezzo">Prezzo:</label><br>
				<input name="prezzo" type="number" min="0" required value="<%=product.getPrezzo()%>"><br>
				
				<label for="marca">Marca:</label><br>
				<input name="marca" type="text" maxlength="20" placeholder="Inserisci marca" required value="<%=product.getMarca()%>"><br>
				
				<label for="disponinbilità">Disponibilità:</label><br>
				<input name="disponibilità" type="text" min=1 required value="<%=product.getDisponibilità()%>"><br>		
				
				<input type="submit" value="Aggiorna">
				<input type="reset" value="Reset">
			</fieldset>
		</form>		
		<%  } %>
		
	<%
		String message = (String)request.getAttribute("message");
		if(message != null && !message.equals("")) {
	%>
		<p style="color: green;"><%=message %></p>
	<%
		}
		if(error != null && !error.equals("")) {
	%>
		<p style="color: red;">Error: <%= error%></p>
	<%}
		}else { %>
	<tr>
		<td colspan="7">No product available</td>
	</tr>	
	<% } %>
	
	</table>
	
	
<form action="<%=response.encodeURL("ProductControl")%>" method="POST">
<fieldset>
				 <legend><b>Insert</b></legend>
				<input type="hidden" name="action" value="insert">
				
				<label for="nome">Nome:</label><br>
				<input name="nome" type="text" maxlength="20" placeholder="Inserisci nome" required value="<%=product.getNome()%>"><br>
				 
				<label for="descrizione">Descrizione:</label><br>
				<textarea name="descrizione" maxlength="100" rows="3" placeholder="Inserisci descrizione" required><%=product.getDescrizione()%></textarea><br>
				
				<label for="prezzo">Prezzo:</label><br>
				<input name="prezzo" type="number" min="0" required value="<%=product.getPrezzo()%>"><br>
				
				<label for="marca">Marca:</label><br>
				<input name="marca" type="text" maxlength="20" placeholder="Inserisci marca" required value="<%=product.getMarca()%>"><br>
				
				<label for="disponinbilità">Disponibilità:</label><br>
				<input name="disponibilità" type="text" min=1 required value="<%=product.getDisponibilità()%>"><br>		
				
				<input type="submit" value="Inserisci">
				<input type="reset" value="Reset">
			</fieldset>
</form>
</body>
</html>