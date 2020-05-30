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
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.PlaylistDao;

@WebServlet("/removePlaylist")
public class RemovePlaylist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lettura parametri
			UserBean user = (UserBean) request.getSession().getAttribute("user");
			String play_id = request.getParameter("play_id");
			
			if(user == null || play_id == null) 
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			else {
				//Dao
				ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
				PlaylistDao playlistDao =  new PlaylistDao(pool);
				
				ArrayList<String> keys = new ArrayList<String>();
				keys.add(play_id);
				
				try {
					//Ricerca playlist
					playlistDao.doDelete(keys);
					response.sendRedirect(response.encodeURL("/heaplay/user/"+user.getUsername()));
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
