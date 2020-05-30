package com.heaplay.control.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heaplay.model.beans.UserBean;

@WebServlet("/admin/operation")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lettura parametri
		String operation = request.getParameter("op");
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		
		//Controllo
		if(user == null || operation == null) {
			//Pagina di errore
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			
			if(operation.equals("register")) {
				//Mando alla pagina di registrazione
				String created = (String) request.getSession().getAttribute("created");
				if(created != null) {
					//Flag della sessione che serve a capire se la registrazione è riuscita
					request.setAttribute("success", created);
					request.getSession().removeAttribute("created");
				}
				//Set degli attributi nella request
				request.setAttribute("jspPath", response.encodeURL("admin/register_new_admin.jsp"));
				request.setAttribute("pageTitle", "Crea amministratore");
				RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/_blank.jsp"));
				rd.forward(request, response);
			} else if(operation.equals("info")) {
				//Inoltro alla pagina di info
				RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/admin/info"));
				rd.forward(request, response);
			} else {
				//Rimando alla home
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
