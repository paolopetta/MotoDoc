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
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.UserDao;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Session
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		
		//Controllo se è già loggato o meno
		if(userBean != null)
			response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/home"));
		else {
			request.setAttribute("jspPath", response.encodeURL("/login.jsp"));
			request.setAttribute("pageTitle", "Login");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/_blank.jsp"));
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
		ArrayList<String> userKeys = new ArrayList<String>();
		String errorMessage = "";
		
		if(userBean != null)													//Controllo esistenza UserBean
			response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/home"));
		else {
			//Controllo parametri
			String email = request.getParameter("email");						
			
			if(email != null && !email.trim().equals("")) {
				request.setAttribute("email", email);
				userKeys.add(email);
			} 
			else 
				errorMessage += "Email non inserita";
			
			String password = request.getParameter("password");
			
			if(password != null && !password.trim().equals("")) {
				userKeys.add(password);
			}
			else 
				errorMessage += " Password non inserita";
			
			if(errorMessage.equals(""))	{
				//Valori inseriti correttamente
				try {
					UserDao userDao = new UserDao(pool);
					userBean = userDao.doRetrieveByKey(userKeys);					
					//Autenticazione dell'utente con redirezione
					if(userBean != null && userBean.getId()!= -1) {
						session.setAttribute("user", userBean);
						response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/home"));
					}
					else {
						errorMessage = "Email o password inserita non valida"; 
						request.setAttribute("errorMessage", errorMessage);
						request.setAttribute("jspPath", "/login.jsp");
						request.setAttribute("pageTitle", "Login");
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/_blank.jsp");
						rd.forward(request, response);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
			}
			else {
				//Rimando al login in caso di errore
				request.setAttribute("errorMessage", errorMessage);
				request.setAttribute("jspPath", "/login.jsp");
				request.setAttribute("pageTitle", "Login");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/blank.jsp");
				rd.forward(request, response);
			}
		}
		
	}
}
