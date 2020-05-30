<%@page import="com.heaplay.model.beans.UserBean"%>
<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
TrackBean playerTrack = (TrackBean) request.getAttribute("track");
UserBean currentUserLocal = (UserBean)session.getAttribute("user"); 
UserBean userPageLocal= (UserBean)request.getAttribute("user");

%>

<div class="song">
	<audio preload="none" class="audio" ontimeupdate="updateCurrentTime(this)">
		<!-- Problemi con il caricamento dell'audio -->
		<% if(playerTrack != null) {%>
		<source
			src="/heaplay/getAudio?id=<%=playerTrack.getId()%>&extension=<%=playerTrack.getTrackExt()%>"
			type="audio/<%=playerTrack.getTrackExt().substring(1)%>">
		<%} %>
	</audio>

	<div class="song-image">
		<%if(playerTrack != null) {%>
		<!-- Conterrà l'immagine della track -->
		<img 
			src="/heaplay/getImage?id=<%=playerTrack.getId()%>&extension=<%=playerTrack.getImageExt()%>"
			alt="Errore">
		<%} %>
	</div>
	
	<div class="song-content">
		<div class="song-info">
			<button class="circle-icon play">
					<i class="fa fa-play color-white"></i>
			</button>
			<div>
				<span class="author"><a href="<%=response.encodeURL("/heaplay/user/"+playerTrack.getAuthorName())%>"><%=playerTrack.getAuthorName()%></a></span><br>
				<span class="song-name"><a href="<%=response.encodeURL("/heaplay/user/"+playerTrack.getAuthorName()+"/"+playerTrack.getName().replaceAll("\\s|!|\\*|\\'|\\(|\\)|\\;|\\:|@|&|=|\\$|\\,|\\/|\\?|\\#|\\[|\\]","")+"?id="+playerTrack.getId()) %>"><%=playerTrack.getName()%></a></span>
			</div>
		</div>
		
		<div class="controls">
			<table>
				<tr>
					<td><span class="song-time">00:00</span></td>
					<td><input type="range"
					name="slider" step="1" class="slider slider-bar"
					oninput="setCurrentTime(this)" value="0" min="0"
					max=<%=playerTrack!=null ? playerTrack.getDuration() : 100%>></td>
					<td><%if(playerTrack != null) {%>
						<span><%=String.format("%02d:%02d", playerTrack.getDuration()/60,playerTrack.getDuration()%60)%></span>
					<%} %></td>
				</tr>
				<tr class="hidden">
					<td><i class="fa fa-volume-down"></i></td>
					<td><input type="range" name="volume" step=".1" class="slider volume"
						oninput="setVolume(this)" value="1" min="0" max="1"></td>
					<td><i class="fa fa-volume-up"></i></td>
				</tr>
			</table>
		</div>
		<div class="song-buttons">
			<span><%=playerTrack.getPlays()%> <%if(playerTrack.getPlays() == 1) { %>riproduzione <%} else { %>riproduzioni<%} %></span>
			<span class="song-button" onclick="like(event)" title="Aggiungi Mi Piace"><i class="<%=playerTrack.isLiked() ? "fa fa-thumbs-up" : "far fa-thumbs-up" %>"></i> <%=playerTrack.getLikes()%></span>
			<%if(playerTrack.isIndexable() && userPageLocal != null && currentUserLocal != null && currentUserLocal.getId() == userPageLocal.getId() ) {%>
			<span class="song-button" onclick="addToPlaylist(this)" title="Aggiungi a una Playlist")><i class="fa fa-plus"></i></span>
			<% }%>
		</div>		
	</div>	
</div>