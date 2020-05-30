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

@WebServlet("/removeFromPlaylist")
public class RemoveTrackFromPlaylist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lettura parametri
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		String track_id = request.getParameter("track_id");
		String play_id = request.getParameter("play_id");
		
		if(user == null || track_id == null || play_id == null) 
			response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/home"));
		else {
			//Dao
			ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
			PlaylistDao playlistDao =  new PlaylistDao(pool);
			
			ArrayList<String> keys = new ArrayList<String>();
			keys.add(play_id);
			PlaylistBean playlist = null;
			ArrayList<TrackBean> list = null;
			
			try {
				//Ricerca playlist
				playlist = (PlaylistBean) playlistDao.doRetrieveByKey(keys);
				list = (ArrayList<TrackBean>) playlist.getTracks();
				//Eliminazione della track
				for(int i = 0; i < list.size() ; i++)
					if(list.get(i).getId() == Long.parseLong(track_id)) {
						list.remove(i);
						playlistDao.doUpdate(playlist);	
						break;
					}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
