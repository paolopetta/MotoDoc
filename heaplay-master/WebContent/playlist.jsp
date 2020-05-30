<%@page import="com.heaplay.model.beans.PlaylistBean"%>
<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
UserBean currentUser = ((UserBean) session.getAttribute("user"));
UserBean userPage = (UserBean) request.getAttribute("userPage");
PlaylistBean playlist = (PlaylistBean) request.getAttribute("playlist");
ArrayList<TrackBean>  listOfTracks = (ArrayList<TrackBean>) playlist.getTracks();
Integer number = (Integer) request.getAttribute("number");
Integer begin = (Integer) request.getAttribute("begin");

%>

<div class="page-header">
	<img class="page-image not-user" src="/heaplay/getImage?id=<%=listOfTracks.get(0).getId()%>&extension=<%=listOfTracks.get(0).getImageExt()%>" onclick = "inputFile($('#image'))"  width="150px">
	<div>
		<span class="page-name"><%=playlist.getName()%></span>
		<span class="page-subname"><a href="<%=response.encodeURL("/heaplay/user/"+playlist.getAuthorName())%>"><%=playlist.getAuthorName()%></a></span>
	</div>
</div>

<%if(currentUser != null && currentUser.getId() == userPage.getId()) {%>
<form style="float: right;"action="<%=response.encodeURL("/heaplay/removePlaylist")%>" method = "POST">
	<input type="hidden" name="play_id" value="<%=playlist.getId()%>">
	<button type="submit" class="item-remove">Elimina Playlist</button>		
</form>
	
<%} %>
	
<div class="playlist-control">
	<button class="circle-icon" title="Indietro" onclick="prev()"><i class="fas fa-chevron-left"></i></button>
	<button class="circle-icon" title="Avanti" onclick="next()"><i class="fas fa-chevron-right"></i></button>
</div>

<div class="user-tracks">
	<%if(listOfTracks.size() == 0) { %>
		<p>Non sono presenti brani</p>
	<%} %>
	
	<%for(int i=0;i<listOfTracks.size();i++) {	
		request.setAttribute("track", listOfTracks.get(i));	
	%>
		<div class="item">
			<%@ include file="/_player.jsp"%>
			<%if(currentUser != null && currentUser.getId() == userPage.getId()) {%>
				<button class="item-remove" onclick="removeFromPlaylist(this)">Rimuovi</button>
			<%} %>
		</div>
	<%} %>
	<br>

<form class="pages-buttons" action="<%=response.encodeURL("/heaplay/user/"+userPage.getUsername()+"/playlist/"+playlist.getName()+"?id="+playlist.getId()) %>" method="POST"> 
		<input type="hidden" value="<%=begin%>" name="begin" id="currentPage">
		<%for( int i= 0; i< number; i+=10) {%>
			<button type="submit" onclick="beginValue(this)" id="<%=i%>"><%=i/10+1%></button>
		<%} %>
	</form>
</div>


<script src="${pageContext.servletContext.contextPath}/js/song.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/users.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/playlist.js" ></script>
