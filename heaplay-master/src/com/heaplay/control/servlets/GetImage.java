package com.heaplay.control.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.TrackDao;
import com.heaplay.model.dao.UserDao;

@WebServlet("/getImage")
public class GetImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//Lettura parametri
    	String id = request.getParameter("id");
    	String ext = request.getParameter("extension"); 
    	String user = request.getParameter("user");
    	
    	
    	if( id == null ) 												//Controllo probabilmente necessario
    		response.sendError(HttpServletResponse.SC_NOT_FOUND);
    	else {
    		byte[] imageBytes = null;
    		
    		try {
				if(user == null && Long.parseLong(id) != -1) {
					//Immagine di una track
					TrackDao trackDao = new TrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
   
					if(ext == null) {
						List<String> keys = new ArrayList<String>();
						keys.add(id);
						TrackBean bean = (TrackBean) trackDao.doRetrieveByKey(keys);
						ext = bean.getImageExt();
					}	
					ext = ext.substring(ext.indexOf('.'), ext.length());
					response.setContentType("image/"+ext);
					//Lettura image
					imageBytes = trackDao.getImage(Long.parseLong(id));
					
				} else if(Long.parseLong(id) != -1){
					//Immagine di un user
					UserDao userDao = new UserDao((ConnectionPool) getServletContext().getAttribute("pool"));
					List<String> keys = new ArrayList<String>();
					keys.add(id);
					UserBean userBean = userDao.doRetrieveByKey(keys);
					
					String extension = (userBean != null) ? userBean.getUserImageExt() : null;
					extension = (extension == null ) ? "png" : extension.substring(extension.indexOf('.'), extension.length());
					response.setContentType("image/" + extension);
					//Lettura image
					imageBytes = userDao.getImage((userBean != null) ? userBean.getId() : 0);	
				}
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    		
    		OutputStream out = response.getOutputStream();
    		if(imageBytes == null ) {
    			//Lettura immagine di default
    			File file = new File(getServletContext().getRealPath("/images/not_found.png"));
    			FileInputStream input = new FileInputStream(file);
    			imageBytes = input.readAllBytes();
    			input.close();
    		}
    		out.write(imageBytes);
    	}
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
