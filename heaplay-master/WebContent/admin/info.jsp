<%@page import="com.heaplay.model.beans.PurchasableTrackBean"%>
<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="java.util.ArrayList"%>
<%
	long iscritti = (Long)request.getAttribute("iscritti");
	long numeroBrani = (Long)request.getAttribute("numeroBrani");
%>

<div>
	<h4>Numero di iscritti: <%=iscritti%></h4>
	<h4>Numero totale di brani caricati: <%=numeroBrani%></h4>

	<nav class="content-nav">
		<a class="mostViewedButton selected" onclick="selectOption($('.mostViewedSongs'),this),getInfo(this)" href="#plays" >Più Ascoltati</a>
		<a class="mostLikedButton" onclick="selectOption($('.mostLikedSongs'),this),getInfo(this)" href="#likes">Più votati</a>
		<a class="mostSoldButton" onclick="selectOption($('.mostSoldSongs'),this),getInfo(this)" href="#sold">Più venduti</a>
	</nav> 
	
	<div id="info-bar"></div>	

</div>

<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	google.charts.load('current', {'packages':['corechart']});
</script>
<script src="${pageContext.servletContext.contextPath}/js/info.js" ></script>
