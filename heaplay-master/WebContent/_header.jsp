<%@page import="com.heaplay.model.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%
	UserBean user = (UserBean)session.getAttribute("user");
%>
<header>
	<div class="menu-bar">
		<div class="logo-wrapper">
			<a title="Homepage" href="<%=response.encodeURL("/heaplay/home") %>"><img class="logo" src="/heaplay/images/logo.svg"></a>		
		</div>	
		<div class="search">
			<div class="custom-select relative-container">
				<select name="filter" class="search-select">
					<option value="track">Brani</option>
					<option value="tag">Tags</option>
					<%if(user == null || !user.getAuth().equals("admin")) { %>
						<option value="playlist">Playlist</option>
					<%} %>
					<option value="user">Utenti</option>
				</select>
			</div><!-- 
			--><input id="search-box" type="text" name="q" placeholder="Cerca..." list="suggestions" onkeyup="autocompleteSearch(this,$('#suggestions'))" onkeypress="searchOnEnterButton(event)"><!--
			--><datalist id="suggestions"></datalist><button class="search-button"><i class="fa fa-search"></i></button>
		</div>
		<nav class="user">
			<%if(user == null) {%>
				<a href=<%=response.encodeURL("/heaplay/login")%>>Login</a>
				<a href="<%=response.encodeURL("/heaplay/register")%>">Registrati</a>
			<%} else { %>
				<div class="dropdown">
					<div class="dropbtn">
						<a href="#"><%=user.getUsername()%> <i class="fa fa-caret-down" style="display: inline"></i></a>
					</div>					
					<div class="dropdown-content <%if(user.getAuth().equals("admin")) {%>drop-admin <%}%>">
						<%if(!user.getAuth().equals("admin")) {%>
							<a href="<%=response.encodeURL("/heaplay/user/" + user.getUsername()) %>">Area Utente</a>
							<a href="<%=response.encodeURL("/heaplay/upload")%>">Carica</a>
						<%} else {%>
							<a href="<%=response.encodeURL("/heaplay/admin/operation?op=register")%>">Registra Admin</a>
							<a href="<%=response.encodeURL("/heaplay/admin/operation?op=info")%>">Info</a>
						<% } %>
						<a href="<%=response.encodeURL("/heaplay/logout")%>">Logout</a>
					</div>
				</div>
				<%if(!user.getAuth().equals("admin")) {%>
				<a href="<%=response.encodeURL("/heaplay/cart")%>"><i class="fa fa-shopping-cart"></i></a>
				<%} %>
				
			<%} %>
		</nav>
	</div>
</header>