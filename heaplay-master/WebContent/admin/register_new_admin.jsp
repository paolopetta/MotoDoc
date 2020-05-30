
<%
	String success = (String) request.getAttribute("success");	
	String error = (String) request.getAttribute("errorMessage");
	String email = (String) request.getAttribute("email");
	String username = (String) request.getAttribute("username");
	if(email == null)
		email = "";
	if(username == null)
		username = "";
%>

<%if(success == null) { %>
	<script src="${pageContext.servletContext.contextPath}/js/validate.js"></script>
		
	<h2>Crea amministratore</h2>
	<hr class="hr-form">
	<form action="<%=response.encodeURL("/heaplay/admin/registerAdmin") %>" onsubmit="return validateForm()" method="post">
		<label for="username">Username*<br/></label>
		<div class="form-field">
			<span class="field-icon"><i class="fa fa-user"></i></span>
			<input type="text" name="username" value="<%=username%>">
		</div>
	
	
		<label for="email">E-Mail*<br/></label>
		<div class="form-field">
			<span class="field-icon"><i class="fa fa-envelope"></i></span>
			<input type="text" name="email" value="<%=email%>"><br/>
		</div>
	
		<label for="password">Password*<br/></label>
		<div class="form-field">
			<span class="field-icon"><i class="fa fa-key"></i></span>
			<input type="password" name="password" value=""><br/>
		</div>
	
		<label for="repeat-password">Ripeti Password*<br/></label>
		<div class="form-field">
			<span class="field-icon"><i class="fa fa-key"></i></span>
			<input type="password" name="repeat-password" value=""><br/>
		</div>
	
		<span class="form-error">
		<%
			if (error != null) {
		%>
		<%=error%>
		<%	} %>
		</span><br/>

		<button type="submit">Registra</button>
	</form>
<% } else {%>
	<h3>Creato con successo</h3>
	<a href="<%=response.encodeURL("/heaplay/admin/operation?op=register")%>">Vuoi registrare un altro admin?</a>
<% }%>