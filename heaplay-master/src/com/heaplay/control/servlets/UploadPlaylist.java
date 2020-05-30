package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.PlaylistBean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.PlaylistDao;
import com.heaplay.model.dao.TrackDao;

@WebServlet("/uploadPlaylist")
public class UploadPlaylist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lettura parametri
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		String track_id = request.getParameter("track_id");
		String playlistName = request.getParameter("playlistName");
		String privacy = request.getParameter("privacy");	
		
		if(user == null || track_id == null || playlistName == null) 
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		else {
			//Dao
			ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
			PlaylistDao playlistDao = new PlaylistDao(pool);
			TrackDao trackDao = new TrackDao(pool);
			
			ArrayList<String> keys = new ArrayList<String>();
			keys.add(playlistName);
			keys.add(user.getId()+"");
			boolean isNew = false;
			
			try {
				PlaylistBean playlist = (PlaylistBean) playlistDao.doRetrieveByKey(keys);
				if(playlist == null) {
					playlist = new PlaylistBean();
					playlist.setAuthor(user.getId());
					playlist.setName(playlistName);
					playlist.setPrivacy(privacy);
					playlist.setAuthorName(user.getUsername());
					isNew = true;
				} 
				keys.clear();
				keys.add(track_id);
				TrackBean track = (TrackBean) trackDao.doRetrieveByKey(keys);
				ArrayList<TrackBean> list = (ArrayList<TrackBean>) playlist.getTracks();
				
				if(!list.contains(track)) {
					list.add(track);
					playlist.setTracks(list);
					if(isNew)
						playlistDao.doSave(playlist);
					else
						playlistDao.doUpdate(playlist);
				} else 
					//Vedere cosa fare; 
					;
				response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/user/" + user.getUsername()));

			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
