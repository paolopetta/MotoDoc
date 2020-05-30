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
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.UserDao;

@WebServlet("/admin/registerAdmin")
public class RegisterAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("jspPath", response.encodeURL("admin/register_new_admin.jsp"));
		request.setAttribute("pageTitle", "Registra admin");
		RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/_blank.jsp"));	
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
    	UserBean userBean = new UserBean();
    	//Lettura parametri
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
    		userBean.setAuth("admin");
    		
    		try {
				userDao.doSave(userBean);
				
				ArrayList<String> keys = new ArrayList<String>();
				keys.add(email);
				keys.add(password);
				userBean = userDao.doRetrieveByKey(keys);
					
			} catch (SQLException e) {
				if(e.getErrorCode() == 1062 && e.getMessage().contains("'username'"))
					error = "Username non disponibile";
				else if(e.getErrorCode() == 1062 && e.getMessage().contains("'email'"))
					error = "Email non disponibile";
				else
					error = "Errore nella registrazione riprovare";
			}
    		
    		//Creazione riuscita e redirezione
			if(userBean != null && userBean.getId() != -1) {
				//Flag per indicare che la creazione è avvenuta con successo
				request.getSession().setAttribute("created", "true");
				response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/admin/operation?op=register"));
			} else {
				//Errore creazione e rinvio alla pagina di registrazione
				request.setAttribute("errorMessage", error);
				request.setAttribute("jspPath", response.encodeURL("admin/register_new_admin.jsp"));
				request.setAttribute("pageTitle", "Registra admin");
				//Forward
				RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/_blank.jsp"));	
				rd.forward(request, response);
			}
    	}
	}

}
