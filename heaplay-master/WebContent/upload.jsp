<%@page import="com.heaplay.model.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String error = (String) request.getAttribute("errorMessage");
	UserBean user = (UserBean)session.getAttribute("user");
	if(user == null)
		response.sendRedirect(getServletContext().getContextPath() + "/home");
%>
<h2>Carica</h2>
<hr class="hr-form">
<form action="<%=response.encodeURL("upload")%>" name="fileUpload" method="POST" enctype="multipart/form-data" onsubmit="return validateForm()" autocomplete="off">
				
	<label for="songName">Nome del Brano*<br/></label>
	<div class="form-field">
		<span class="field-icon"><i class="fa fa-music"></i></span>
		<input type="text" name="songName" id="songName" />
	</div>
	
	<div class="form-field" id="track-field">
		<label class="file-input">Carica il Brano<input class="form-input-file" type="file" name="audio" id="audio" accept="audio/*" /></label>
		<span class="cross"><i class="fa fa-times"></i></span>
		<span class="check"><i class="fa fa-check"></i></span>
	</div>
	
	<div class="form-field" id="image-field" style="align-items: center">
		<label class="file-input">Carica l'Immagine<input class="form-input-file" type="file" name="image" id="image" accept="image/*" /></label>
		<span class="cross"><i class="fa fa-times"></i></span>
		<div class="song-image hidden" style="margin-left: 15px">
			<img id="preview" alt="" src="">
		</div>
	</div>
	
	<div class="form-field">
		<input class="form-input-radio" type="radio" name="purchasable" id="Free" value="Gratis" checked="checked" onclick="ShowAndHide(0)"> 
		<label for="Free">Gratis </label>
		
		<input class="form-input-radio" type="radio" name="purchasable" id="purchasable" value="A pagamento" onclick="ShowAndHide(1)">	
		<label for="purchasable">A pagamento</label>
	</div>
	
	<div id="divPrice" class="hidden">
		<label for="price">Prezzo*<br/></label>
		<div class="form-field">
			<span class="field-icon"><i class="fa fa-dollar-sign"></i></span>
			<input type="text" name="price" id="price" min="0" max="666">
		</div>
	</div>
		
	<label for="tags">Tag*<br/></label>
	<div class="form-field">
		<span class="field-icon"><i class="fa fa-hashtag"></i></span>
		<input id="autocomplete" type="text" name="tags" list="tagSuggestions" onkeyup="autocompleteTags(this,$('#tagSuggestions'))"/>
		<button type="button" id="tagButton" onclick="addTag(this)" >Aggiungi</button>
	</div>
	<datalist id="tagSuggestions"></datalist>
	
	<div class="added-tags"></div>
		
	<input type="hidden" name="authorId" value="<%=user.getId()%>">
	<input type="hidden" name="duration" id="duration"> 
	
	<span class="form-error">
	<%
		if (error != null) {
	%>
	<%=error%>
	<%	} %>
	</span><br/>
	
	<button type="submit">Carica</button>
	<audio id="audioFake">
	</audio>
</form>
	
<script src="${pageContext.servletContext.contextPath}/js/uploadFunction.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/validate.js"></script>