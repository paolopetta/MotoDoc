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

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		
		if(userBean != null)														//Se già loggato lo mando alla Home
			response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/home"));
		else {
			request.setAttribute("jspPath", response.encodeURL("/register.jsp"));
			request.setAttribute("pageTitle", "Registrati");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/_blank.jsp"));	//Altrimenti lo mando alla pagian di registrazione
			rd.forward(request, response);
		}
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//Lettura dei parametri
    	ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
    	UserBean userBean = new UserBean();
    	String username = request.getParameter("username");				
    	String password = request.getParameter("password");
    	String email = request.getParameter("email");
    	String error = "";
    	
    	//Controllo parametri
    	if(username != null && !username.trim().equals(""))
    		request.setAttribute("username", username);
    	else
    		error += "Username non inserito ";
  
    	if(password != null && !password.trim().equals(""))
    		request.setAttribute("password", password);
    	else 
    		error += " Password non inserita ";
    	
    	if(email != null && !email.trim().equals(""))
    		request.setAttribute("email", email);
    	else
    		error += "Email non inserita ";
    	
    	//Creazione dell'account
    	if(error.equals("")) {
    		UserDao userDao = new UserDao(pool);
    		
    		userBean.setEmail(email);
    		userBean.setPassword(password);
    		userBean.setUsername(username);
    		userBean.setActive(true);
    		userBean.setAuth("user");
    		
    		try {
				//Creazione utente
    			userDao.doSave(userBean);
				//Lettura dell'utente
    			ArrayList<String> keys = new ArrayList<String>();
				keys.add(email);
				keys.add(password);											
				userBean = userDao.doRetrieveByKey(keys);
					
			} catch (SQLException e) {
				if(e.getErrorCode() == 1062 && e.getMessage().contains("'username'"))
					error = "Username già in uso";
				else if(e.getErrorCode() == 1062 && e.getMessage().contains("'email'"))
					error = "Email già in uso";
				else
					error = "Errore nella registrazione riprovare";
			}
    		
    		//Creazione riuscita e redirezione
			if(userBean != null && userBean.getId() != -1) {
				request.getSession().setAttribute("user", userBean);
				response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/home"));
			} else {
				//Errore creazione e rinvio alla pagina di registrazione
				request.setAttribute("errorMessage", error);
				request.setAttribute("jspPath", "/register.jsp");
				request.setAttribute("pageTitle", "Registrati");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/_blank.jsp");	//Altrimenti lo mando alla pagina di registrazione
				rd.forward(request, response);
			}
    	}
	}
}
