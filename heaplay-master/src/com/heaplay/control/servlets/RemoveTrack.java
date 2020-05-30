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
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.TrackDao;

@WebServlet("/removeTrack")
public class RemoveTrack extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lettura parametri
		String track_id = request.getParameter("track_id");
		String disable = request.getParameter("disable");
		String enable = request.getParameter("enable");
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		
		if(track_id == null || user == null) {
			//Pagina di errore
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			
			TrackDao trackDao = new TrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
			ArrayList<String> keys = new ArrayList<String>();
			keys.add(track_id);
			
			try {
				
				//Lettura track
				TrackBean track = (TrackBean) trackDao.doRetrieveByKey(keys); 
				
				if(track == null) {
					//Fare qualcosa
				} else {
					if(!user.getAuth().equals("admin") && track.getAuthor() != user.getId()) {
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					} else if(enable != null) {
						//Riabilito la track
						track.setIndexable(true);
						trackDao.doUpdate(track);
					} else if(track.getType().equals("pagamento") || disable != null ) {
						//Disabilito la track
						track.setIndexable(false);
						trackDao.doUpdate(track);
					} else {
						//La cancello
						trackDao.doDelete(keys);
					}
					if(!user.getAuth().equals("admin") && track.getAuthor() == user.getId())
						response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/home"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}

}
