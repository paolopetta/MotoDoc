package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.PurchasableTrackDao;
import com.heaplay.model.dao.TrackDao;

@WebServlet("/getInfo")
public class GetInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tracksToSelect = request.getParameter("select");
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		//Dao
		ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
		TrackDao trackDao = new TrackDao(pool);
		PurchasableTrackDao purchasableTrackDao = new PurchasableTrackDao(pool);
		
		if(user != null && user.getAuth().equals("admin") && tracksToSelect != null) {
			response.setContentType("application/json");
			try {
				//Lettura mediante i vari Dao
				ArrayList<TrackBean> list = null;
				switch(tracksToSelect) {
				case "likes" : list = trackDao.getTracksByParameter("likes", 0, 5);
								break;
				case "sold"	 : list = purchasableTrackDao.getMostSoldTracks();
								break;	
				default : list = trackDao.getTracksByParameter("plays", 0, 5);
								break;

				}
				
				resetBytes(list);	
				
				Gson gson = new Gson();
				String jsonObject = gson.toJson(list);
				response.getWriter().write(jsonObject);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private static void  resetBytes(ArrayList<TrackBean> list) {			
		for(int i=0;i<list.size();i++) {
			TrackBean track = (TrackBean) list.get(i);
			track.setImage(null);
			track.setTrack(null);
		}
	}

}
