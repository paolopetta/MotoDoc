package com.heaplay.control.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.Bean;
import com.heaplay.model.beans.TagBean;
import com.heaplay.model.dao.TagDao;



@WebServlet("/getTags")
public class GetTags extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Setto il content-type a json
		response.setContentType("application/json");
		//Creazione di Dao e GSON
		ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool"); 
		TagDao tagDao = new TagDao(pool);
		Gson gson = new Gson();
		
		try {
			//Query al DB
			ArrayList<Bean> listOfTags = (ArrayList<Bean>)tagDao.doRetrieveAll((a,b)->{
				TagBean tagA = (TagBean)a;
				TagBean tagB = (TagBean)b;
				return tagA.getName().compareToIgnoreCase(tagB.getName());
			});
			//Lettura parametri e selezione nella lista di quelli che corrispondono
			String term = request.getParameter("query");			
			listOfTags = (ArrayList<Bean>) listOfTags.stream().filter(p -> ((TagBean)p).getName().contains(term)).collect(Collectors.toList());
			
			//Creazione array
			ArrayList<String> arrayOfTags = new ArrayList<String>();
			listOfTags.forEach((bean) -> {
				arrayOfTags.add(((TagBean) bean).getName());
			});

			//Creazione oggetto JSON
			Type type = new TypeToken<String[]>() {}.getType();
			PrintWriter out = response.getWriter();
			String gsonObject = gson.toJson(arrayOfTags.toArray(), type);
			out.write(gsonObject); 	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}	

}
