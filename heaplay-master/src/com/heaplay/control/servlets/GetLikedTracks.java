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
import com.heaplay.model.beans.Bean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.TrackDao;
import com.heaplay.model.dao.UserDao;

@WebServlet("/getLikedTracks")
public class GetLikedTracks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		response.setContentType("application/JSON");
 		ArrayList<Bean> list = new ArrayList<Bean>();
 		UserBean user = (UserBean) request.getSession().getAttribute("user");
 		ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
 		TrackDao trackDao = new TrackDao(pool);
 		UserDao userDao = new UserDao(pool);
 		
 		try {
			//Filtro delle tracks
 			list = (ArrayList<Bean>) trackDao.doRetrieveAll((a,b) -> new Long(((TrackBean)b).getLikes() - ((TrackBean)a).getLikes()).intValue());
			list = (ArrayList<Bean>) list.stream().filter((p) ->((TrackBean)p).isIndexable()).collect(Collectors.toList());
			ArrayList<Bean> listOfObjects = new ArrayList<Bean>();
			//Selezione delle prime 5
			int size = list.size();
			if(size > 0)
				listOfObjects.addAll(list.subList(0, (size < 9) ? size : 9));
			if(user != null)
				for(int i=0; i < listOfObjects.size(); i++)
					((TrackBean)listOfObjects.get(i)).setLiked(userDao.checkIfLiked(user.getId(),((TrackBean)listOfObjects.get(i)).getId()));
			//Conversione in JSON
			Gson gson = new Gson();
			String objectJson = gson.toJson(listOfObjects);
			response.getWriter().write(objectJson);
			
 		} catch (SQLException e) {
			e.printStackTrace();	
		}
 	}

 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
 	
	@SuppressWarnings("unused")
	private static void resetBytes(List<Bean> array) {			
		for(int i=0;i<array.size();i++) {
			TrackBean track = (TrackBean) array.get(i);
			track.setImage(null);
			track.setTrack(null);
		}
	}
}
