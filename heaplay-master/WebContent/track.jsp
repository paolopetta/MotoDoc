<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.Cart"%>
<%@page import="com.heaplay.model.beans.PurchasableTrackBean"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="com.heaplay.model.beans.TagBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
TrackBean track = (TrackBean)request.getAttribute("currentTrack"); 
Cart<TrackBean> cart = (Cart<TrackBean>)session.getAttribute("cart");
String owned = (String)request.getAttribute("owned");
UserBean user = (UserBean) session.getAttribute("user");
if(track != null ) {%>

	<div class="flex-container between">
		<div class="track-info">
			<%request.setAttribute("track", track);%>
			<%@ include file="/_player.jsp"%>
			<div class="added-tags">
				<%ArrayList<TagBean> tags = (ArrayList<TagBean>) track.getTags();
				for(int i = 0; i < tags.size(); i++) { %>
					<span class="tag"><%=tags.get(i).getName()%></span>
				<%} %>
			</div>
		</div>
	<%
	if(user != null) {
		if(user.getId() != track.getAuthor() && !user.getAuth().equals("admin")) {	%>
			<div class="add-cart">
				<%if(track.getType().equals("pagamento")) { 
					PurchasableTrackBean pTrack = (PurchasableTrackBean) track;
				%>
					<span>Prezzo: <span class="price"><%=(String.format("%.2f", pTrack.getPrice()))%> €</span></span>
				<%} else {%>
					<span class="price">Gratuita</span>
				<%} %>
				<br>
				<%if(!cart.getItems().contains(track) && owned.equals("false")) { %>
					<button onclick="addToCart(this)">Aggiungi al carrello</button>
				<%} else if(owned.equals("false")){%>
					<span>Già aggiunta al carrello</span>
				<%} else {%>
					<span><b>Acquistata</b></span>
				<%} %>
			</div>
		<%} else if(user.getId() == track.getAuthor()) { %>
				<form action="<%=response.encodeURL("/heaplay/removeTrack")%>" method ="POST">
					<input type="hidden" name="track_id" value="<%=track.getId()%>">
					<button type="submit">Rimuovi</button>
				</form>
		<% } %>
	<%}%>
	</div>
<%} %>
	


<%if(user == null) { %>
	<span><b><a href="/heaplay/login">Accedi</a></b> oppure <b><a href="/heaplay/register">registrati</a></b> per poter commentare.</span><a></a>
<%} %>
<h3>Commenti</h3>
<hr class="hr-form">
<%if(user != null && !user.getAuth().equals("admin")) {%>
<div class="write-comment">
		<textarea class="form-input-textarea" maxlength="255" placeholder="Scrivi un commento"></textarea>
		<button onclick="uploadComment(this)">Invia</button>
</div>
<%} %>
<div class="comment-container"></div>


<script src="${pageContext.servletContext.contextPath}/js/song.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/users.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/comment.js" ></script>
