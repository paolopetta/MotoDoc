package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.OwnedTrackDao;
import com.heaplay.model.dao.TrackDao;
import com.heaplay.model.dao.UserDao;

@WebServlet("/filter/author")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lettura parametri
		String user = (String) request.getAttribute("userName");
    	UserBean userBean = (UserBean) request.getSession().getAttribute("user");
		Integer begin = request.getParameter("begin") == null ? 0 : Integer.parseInt(request.getParameter("begin"));
    	String owned = request.getParameter("track");
		UserBean currentUser = null;
		ArrayList<TrackBean> listOfTracks = null;
		int numberOfTracks = 0;
		
		try {
			ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
			UserDao userDao = new UserDao(pool);
			TrackDao trackDao = new TrackDao(pool);
			OwnedTrackDao ownedTrackDao = new OwnedTrackDao(pool);
			boolean flagError = false;
			
			currentUser = userDao.doRetrieveByName(user);
			if(currentUser != null && currentUser.isActive() && !currentUser.getAuth().equals("admin")) {	//Controllo se l'utente esiste ed � attivo
				if(owned == null) {
					//Tracks caricate
					listOfTracks = trackDao.getTracksByAuthor(currentUser.getId(),begin,9,"");
					numberOfTracks = trackDao.getNumberOfTracksOfAuthor(currentUser.getId());
				} else if(userBean != null && userBean.getId() == currentUser.getId()){
					//Track acquistate
					listOfTracks = (ArrayList<TrackBean>) ownedTrackDao.getOwnedTrackByUser(currentUser.getId(), begin,9);
					numberOfTracks = ownedTrackDao.getNumberOfTrackByUser(currentUser.getId());
					request.setAttribute("owned", owned);	//Flag
				} else {
					HttpServletResponse resp = response;
					resp.sendError(HttpServletResponse.SC_NOT_FOUND);
					flagError = true;
				}
				
				if(!flagError) {
					if(userBean != null)
						for(int i = 0 ; i < listOfTracks.size() ; i++)
							listOfTracks.get(i).setLiked(userDao.checkIfLiked(userBean.getId(), listOfTracks.get(i).getId()));
					
					request.setAttribute("user", currentUser);
		    		request.setAttribute("tracks", listOfTracks);
		    		request.setAttribute("begin", begin);
		    		request.setAttribute("numberOfTracks",numberOfTracks);
		    		request.setAttribute("jspPath", response.encodeRedirectURL("/user.jsp"));
					request.setAttribute("pageTitle", user);
					
					RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeRedirectURL("/_blank.jsp"));
					rd.forward(request, response);
				}
			}else {
				//Pagina di errore
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
