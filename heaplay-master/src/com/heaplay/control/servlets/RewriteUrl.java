package com.heaplay.control.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/user/*")
public class RewriteUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//Lettura URL e parametri
    	String URI = request.getRequestURI();
    	String[] params = URI.split("/");
    	
    	if(params.length == 5 && params[params.length - 3].equals("user")) {
    		//Pagina della track
    		request.setAttribute("userName", params[params.length-2]);
    		request.setAttribute("trackName",  params[params.length-1].substring(0, params[params.length-1].indexOf(';') != -1 ? params[params.length-1].indexOf(';') : params[params.length-1].length())); //Per evitare problemi nel URL rewriting
    		//Forward
    		RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/filter/track"));
    		rd.forward(request, response);
    	} else if(params.length == 6 && params[params.length - 2].equals("playlist") && params[params.length - 4].equals("user")){
    		//Pagina della playlist
    		request.setAttribute("userName", params[params.length-3]);
    		request.setAttribute("playlistName",  params[params.length-1].substring(0, params[params.length-1].indexOf(';') != -1 ? params[params.length-1].indexOf(';') : params[params.length-1].length()));
    		//Forward
    		RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/filter/playlist"));
    		rd.forward(request, response);
    	} else if(params.length == 4 && params[params.length - 2].equals("user")){
    		//Pagina dell'utente
    		request.setAttribute("userName", params[params.length-1].substring(0, params[params.length-1].indexOf(';') != -1 ? params[params.length-1].indexOf(';') : params[params.length-1].length()));
	    	//Forward
    		RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/filter/author"));
    		rd.forward(request, response);
    	} else {
    		//Pagina di errore
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
    	}	
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
