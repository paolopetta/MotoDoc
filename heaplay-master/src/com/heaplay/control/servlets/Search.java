package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.Bean;
import com.heaplay.model.beans.PlaylistBean;
import com.heaplay.model.beans.TagBean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.PlaylistDao;
import com.heaplay.model.dao.TagDao;
import com.heaplay.model.dao.TrackDao;
import com.heaplay.model.dao.UserDao;

@WebServlet("/search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Set del Content Type
		response.setContentType("application/json");
		//Lettura parametri
		String query = request.getParameter("q");
		String filter = request.getParameter("filter");
		String autocomplete = request.getParameter("auto");
		String fromStart = request.getParameter("startFrom");
		int start = Integer.parseInt(fromStart==null ? "0" : fromStart);
		int found = 0;
		UserBean user =(UserBean) request.getSession().getAttribute("user");
		
		if(query != null && filter != null && !filter.equals("")) {
			ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
			ArrayList<Bean> list = new ArrayList<Bean>();
			int numberOfTracks = (user!=null && user.getAuth().equals("admin")) ? 10 : 5;
			UserDao userDao = new UserDao(pool);
			try {
				//In base al filtro
				switch(filter) {
				case "user": list = (ArrayList<Bean>) userDao.doRetrieveAll(null);
							if(!query.equals(""))
								list = filter(query, list);
							
							if(user == null || !user.getAuth().equals("admin"))
								list = (ArrayList<Bean>) list.stream().filter((p)->((UserBean)p).isActive() && ((UserBean)p).getAuth().equals("user")).collect(Collectors.toList());
							else 
								list = (ArrayList<Bean>) list.stream().filter((p)->((UserBean)p).getId()!=user.getId()).collect(Collectors.toList());
							
							found = list.size();
							if(autocomplete == null)
								list = createSubList(list,start,(start+numberOfTracks) > found ? found : (start+numberOfTracks));
							break;
				case "track":TrackDao trackDao = new TrackDao(pool);
							list = (ArrayList<Bean>) trackDao.doRetrieveAll(null);
							if(!query.equals(""))
								list = filter(query, list);
							if(user == null || !user.getAuth().equals("admin"))
								list = (ArrayList<Bean>) list.stream().filter((p)->((TrackBean)p).isIndexable()).collect(Collectors.toList());
							found = list.size();
							if(autocomplete == null) {
								list = createSubList(list,start,(start+numberOfTracks) > found ? found : (start+numberOfTracks));	
								if(user != null)
									for(int i=0;i<list.size();i++)
										((TrackBean)list.get(i)).setLiked(userDao.checkIfLiked(user.getId(),((TrackBean)list.get(i)).getId()));
							}
							break;
				case "playlist":PlaylistDao playlistDao = new PlaylistDao(pool);
							list = (ArrayList<Bean>) playlistDao.doRetrieveAll(null);
							list = filter(query, list);
							list=(ArrayList<Bean>) list.stream().filter(p ->((PlaylistBean)p).getPrivacy().equals("public")).collect(Collectors.toList());
							found = list.size();
							if(autocomplete == null)
								list = createSubList(list,start,(start+numberOfTracks) > found ? found : (start+numberOfTracks));
							for(int i=0;i<list.size();i++) {
								if(user != null) {
									ArrayList<TrackBean> listOfTracks = (ArrayList<TrackBean>) ((PlaylistBean)list.get(i)).getTracks();
									for(int j=0;j<listOfTracks.size();i++)
										listOfTracks.get(i).setLiked(userDao.checkIfLiked(user.getId(),listOfTracks.get(i).getId()));
								}
							}	
							break;
				case "tag":  TagDao tagDao = new TagDao(pool);
							TrackDao track = new TrackDao(pool);
							list = (ArrayList<Bean>) tagDao.doRetrieveAll(null);
							if(!query.equals(""))
								list = filter(query, list);
							found = list.size();
							if(autocomplete == null && (user == null || !user.getAuth().equals("admin"))) {
								ArrayList<Bean> listOfTags = new ArrayList<Bean>();
								for(int i = 0 ; i < found ; i++)
									listOfTags.addAll(track.getTracksByTag(((TagBean)list.get(i)).getId()));
								if(user == null || !user.getAuth().equals("admin"))
									list = (ArrayList<Bean>) listOfTags.stream().filter((p)->((TrackBean)p).isIndexable()).collect(Collectors.toList());
								found = list.size();
								list = createSubList(list,start,(start+numberOfTracks) > found ? found : (start+numberOfTracks));
								if(user != null)
									for(int i=0;i<list.size();i++)
										((TrackBean)list.get(i)).setLiked(userDao.checkIfLiked(user.getId(),((TrackBean)list.get(i)).getId()));
							}
							break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			Gson gson = new Gson();
			String objectJson = null;
			
			// Creazione dell'oggetto JSON in base all'uso in base all'autocompletamento o meno
			if(autocomplete == null)
				objectJson = "{\"list\":"+gson.toJson(list)+", \"length\":"+found+"}";
			else {
				String[] suggestions = new String[list.size()];
				for(int i=0;i<list.size();i++)
					suggestions[i] = list.get(i).getBeanName() ;
				objectJson = gson.toJson(suggestions);
			}
			response.getWriter().write(objectJson);
		}
		else 
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private ArrayList<Bean> filter(String query,ArrayList<Bean> list) {
		Pattern regex = Pattern.compile("^.*" + query + ".*$", Pattern.CASE_INSENSITIVE); /* equivale a /^.* " + query + ".*$/i */
		
		ArrayList<Bean> newList=(ArrayList<Bean>) list.stream().filter(p ->regex.matcher(p.getBeanName()).matches()).collect(Collectors.toList());
		
		return newList;
	}

	
	private static ArrayList<Bean> createSubList(ArrayList<Bean> list, int begin, int end) {
		ArrayList<Bean> sublist = new ArrayList<Bean>();
		for(int i = begin; i < end; i++)
			sublist.add(list.get(i));
		return sublist;
	}

}