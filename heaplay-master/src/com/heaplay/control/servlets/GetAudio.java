package com.heaplay.control.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.dao.TrackDao;

@WebServlet("/getAudio")
public class GetAudio extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lettura parametri
		String id = request.getParameter("id");
    	String ext = request.getParameter("extension");
    	
    	if( id == null || ext == null) 												//Controllo probabilmente necessario
    		response.sendError(HttpServletResponse.SC_NOT_FOUND);
    	else {
    		//Set content type
    		ext = ext.substring(ext.indexOf('.')+1, ext.length());
    		response.setContentType("audio/"+ext);
    		response.setHeader("Accept-Ranges", "bytes");
    		
    		TrackDao trackDao = new TrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
    		byte[] trackBytes = null;
    		ArrayList<String> keys = new ArrayList<String>();
    		keys.add(id);
    		
    		
    		try {
    			//Lettura bytes
				trackBytes = trackDao.getAudio(Long.parseLong(id));
				response.setContentLengthLong(trackBytes.length);
	    		
    		} catch (SQLException | NumberFormatException e) {
				e.printStackTrace();
			}
    		
    		OutputStream out = response.getOutputStream();
    		if(trackBytes == null ) {
    			//Facciamo qualcosa
    		} else 
    			out.write(trackBytes);
    	}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
