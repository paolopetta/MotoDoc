package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.PlaylistBean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.dao.PlaylistDao;

@WebServlet("/getBestPlaylists")
public class GetBestPlaylists extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//SetContentType
		response.setContentType("application/json");
		PlaylistDao playlistDao = new PlaylistDao((ConnectionPool) getServletContext().getAttribute("pool"));
		
		try {
			//Presa delle playlist
			ArrayList<PlaylistBean> list = (ArrayList<PlaylistBean>) playlistDao.getMostViewedPlaylists(9);
			for(int i = 0 ; i < list.size() ; i++) {
				//Eliminazione dei bytes
				resetBytes(list.get(i).getTracks());
				//Selezione solo dei brani disponibili
				List<TrackBean> listOfTracks = (List<TrackBean>) list.get(i).getTracks().stream().filter((p) ->p.isIndexable()).collect(Collectors.toList());
				list.get(i).setTracks(listOfTracks);
			}	
			//Conversione in stringa JSON
			Gson gson = new Gson();
			String object = gson.toJson(list);
			response.getWriter().write(object);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private static void  resetBytes(List<TrackBean> list) {			
		for(int i=0;i<list.size();i++) {
			TrackBean track = list.get(i);
			track.setImage(null);
			track.setTrack(null);
		}
	}
}
