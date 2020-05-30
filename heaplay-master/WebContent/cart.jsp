<%@page import="com.heaplay.model.beans.PurchasableTrackBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="com.heaplay.model.beans.Cart"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	UserBean user = (UserBean)session.getAttribute("user");
	Cart<TrackBean> cart = (Cart<TrackBean>) session.getAttribute("cart");
	double sum = 0;
%>

<h2>Carrello</h2>
<hr class="hr-form">
<div class="cart">
	<%
	ArrayList<TrackBean> list = (ArrayList<TrackBean>)cart.getItems();
	for(int i = 0 ; i < list.size() ; i++) {	
		TrackBean track = list.get(i);
		sum += track.getType().equals("free") ? 0 : (((PurchasableTrackBean)track).getPrice());
	%>
		<div class="item">
			<div class="carted">
				<div class="song-image">
					<img 
						src="/heaplay/getImage?id=<%=track.getId()%>&extension=<%=track.getImageExt()%>"
					alt="Errore">
				</div>
				<div class="carted-content">
					<span class="author"><a href="<%=response.encodeURL("/heaplay/user/"+track.getAuthorName()+"/"+track.getName().replaceAll("\\s","")+"?id="+track.getId()) %>"><%=track.getAuthorName()%></a></span> <br>
					<span class="song-name"><a href="/heaplay/user/<%=track.getAuthorName()%>/<%=track.getName().replaceAll("\\s","")%>?id=<%=track.getId()%>"><%=track.getName()%></a></span> <br>
					<span>Prezzo: <span class="price"><%=(track.getType().equals("free")) ? "Gratuita" : (String.format("%.2f",((PurchasableTrackBean)track).getPrice())) + "€"%></span></span>
					<span class="upload-date hidden">Aggiunto in Data: <%=track.getUploadDate()%></span><br>
				</div>
			</div>
			<button class="item-remove" onclick="removeFromCart(this)">Rimuovi</button>
		</div>
	<%} 
	if(list.size() == 0) { %>
		<p>Il tuo carrello è al momento vuoto</p>
	<%} else {%>
		<br>
		<div class="cart-sum" >
			<span>Prezzo Totale: <span id="sum" class="price"><%=String.format("%.2f",sum)%> €</span></span> <br>
		</div>
		<button onclick="purchase()">Acquista</button>
	<%} %>
</div>
<div class="loading-cart hidden">
	<img alt="Loading..." src="/heaplay/images/loading.gif" width="50px">
</div>

<script src="${pageContext.servletContext.contextPath}/js/cart.js" ></script>
