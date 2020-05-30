package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.Cart;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.TrackDao;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lettura del carrello
		HttpSession session = request.getSession();
    	UserBean user = (UserBean) session.getAttribute("user");
    	Cart<TrackBean> cart = (Cart<TrackBean>) session.getAttribute("cart"); 
    	
    	//Controllo
    	if(user == null) 
    		response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/login"));
    	else {
    		if(cart == null) {
    			//Lettura o creazione del carrello se non è ancora nella sessione
    			TrackDao trackDao = new TrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
    			cart = new Cart<TrackBean>();
    			try {
					cart.setItems(trackDao.getCart(user.getId()));
					session.setAttribute("cart", cart);	//Salvataggio nella sessione
				} catch (SQLException e) {
					e.printStackTrace();
				}
    		}
    		//Set degli attributi e forward
    		request.setAttribute("jspPath", response.encodeURL("/cart.jsp"));
			request.setAttribute("pageTitle", "Carrello");
			RequestDispatcher rd = getServletContext().getRequestDispatcher( response.encodeURL("/_blank.jsp"));
			rd.forward(request, response);
    	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
