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
import javax.servlet.http.HttpSession;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.Cart;
import com.heaplay.model.beans.OwnedTrackBean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.OwnedTrackDao;
import com.heaplay.model.dao.PurchasableTrackDao;
import com.heaplay.model.dao.TrackDao;
import com.heaplay.model.dao.UserDao;


@WebServlet("/filter/track")
public class Track extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lettura dei parametri
		String id = request.getParameter("id");
		String userName = (String) request.getAttribute("userName");
		String trackName = (String) request.getAttribute("trackName");
		HttpSession session = request.getSession();
		Cart<TrackBean> cart = (Cart<TrackBean>) session.getAttribute("cart");
		UserBean user = (UserBean) session.getAttribute("user");
		
		if(id == null || userName == null || trackName == null)
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		else {
			//Dao
			ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
			TrackDao trackDao = new TrackDao(pool);
			
			ArrayList<String> keys = new ArrayList<String>();
			keys.add(id);
			TrackBean track = null;
			String owned = "false";
			
			try {
				//Lettura della track
				track = (TrackBean) trackDao.doRetrieveByKey(keys);
				if(track.getType().equals("pagamento")) {
					PurchasableTrackDao purchasableTrackDao = new PurchasableTrackDao(pool);
					track = (TrackBean) purchasableTrackDao.doRetrieveByKey(keys); 
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			
			if(track == null || !track.isIndexable() || !track.getAuthorName().equals(userName) || !track.getName().replaceAll("\\s|!|\\*|\\'|\\(|\\)|\\;|\\:|@|&|=|\\$|\\,|\\/|\\?|\\#|\\[|\\]","").equals(trackName)) {
				//pagina di errore
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				if(user !=  null) {
					try {
						if(cart == null) {
							//Creazione del carrello
							cart = new Cart<TrackBean>();
							cart.setItems(trackDao.getCart(user.getId()));
							session.setAttribute("cart", cart);	
						}
						
						OwnedTrackDao ownedTrackDao = new OwnedTrackDao(pool);
						keys.clear();
						keys.add(track.getId()+"");
						keys.add(user.getId()+"");
						
						OwnedTrackBean bean = (OwnedTrackBean) ownedTrackDao.doRetrieveByKey(keys);
						owned = (bean == null) ? "false" : "true";
						request.setAttribute("owned", owned);
						
						UserDao userDao = new UserDao(pool);
						track.setLiked(userDao.checkIfLiked(user.getId(), track.getId()));
						
						
					} catch (SQLException e) {
						e.printStackTrace();
						response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					}
				}
				//Set degli attributi
				request.setAttribute("currentTrack",track);
				request.setAttribute("jspPath", response.encodeURL("/track.jsp"));
				request.setAttribute("pageTitle", track.getName());
				RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/_blank.jsp"));
				rd.forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
