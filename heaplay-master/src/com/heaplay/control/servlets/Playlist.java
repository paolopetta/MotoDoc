package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.PlaylistBean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.OwnedTrackDao;
import com.heaplay.model.dao.PlaylistDao;
import com.heaplay.model.dao.UserDao;

@WebServlet("/filter/playlist")
public class Playlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lettura dei parametri
   		String user = (String) request.getAttribute("userName");
		String playlistName = (String) request.getAttribute("playlistName");
		String begin = request.getParameter("begin");
		String id = request.getParameter("id"); 
		UserBean currentUser = (UserBean) request.getSession().getAttribute("user");
		int start = (begin == null || Integer.parseInt(begin) == 0) ? 0 : Integer.parseInt(begin)+1;
		
		if(user == null || playlistName == null)
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		else {
			try {
				//Dao
				ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
				UserDao userDao = new UserDao(pool);
				PlaylistDao playlistDao = new PlaylistDao(pool);
				//Query al DB
				UserBean userBean = userDao.doRetrieveByName(user);
				if(userBean == null) {
					/*Pagina di errore*/
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				} else {
					ArrayList<String> keys = new ArrayList<String>();
					keys.add(id);
					PlaylistBean playlistBean = (PlaylistBean) playlistDao.doRetrieveByKey(keys);
					//Controllo nome playlist e se non � privata
					if(playlistBean == null || !playlistBean.getName().replaceAll("\\s|!|\\*|\\'|\\(|\\)|\\;|\\:|@|&|=|\\$|\\,|\\/|\\?|\\#|\\[|\\]","").equals(playlistName) || !playlistBean.getAuthorName().equals(user) || (playlistBean.getPrivacy().equals("private") && (currentUser == null || !playlistBean.getAuthorName().equals(currentUser.getUsername())))) {
						/*Pagina di errore*/
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					} else {
						//Lettura delle tracks della playlist
						ArrayList<TrackBean> list = (ArrayList<TrackBean>) playlistBean.getTracks();
						int size = list.size();
						//Selezione delle tracks
						if(playlistBean.getPrivacy().equals("public"))
							list = (ArrayList<TrackBean>) list.stream().filter((p) ->p.isIndexable()).collect(Collectors.toList());
						if(size > list.size()) {
							//Aggiornamento delle tracks nella playlist eliminando quelle non pi� disponibili (Bloccate)
							size = list.size();
							playlistBean.setTracks(list);
							playlistDao.doUpdate(playlistBean);
						}
						// Aggiunto da Marco, controllare
						if(currentUser == null) {
							for(int i = 0; i < list.size(); i++)
								if(list.get(i).getType().equals("pagamento")) 
										list.remove(i);
						}
						else if(!currentUser.getUsername().equals(user)) {
							OwnedTrackDao owTrackDao = new OwnedTrackDao(pool);
							ArrayList<TrackBean> ownedTracks = (ArrayList<TrackBean>) owTrackDao.getOwnedTrackByUser(currentUser.getId(), 0, Integer.MAX_VALUE);
							
							for(int i = 0; i < list.size(); i++) {
								if(list.get(i).getType().equals("pagamento")) {
									boolean found = false;
									for(int j = 0; j < ownedTracks.size(); j++) 
										if(ownedTracks.get(j).getId() == list.get(i).getId()) {
											found = true;
											break;
										}
									if(!found)
										list.remove(i);
								}
							}
							size = list.size();
						}
						//Sottolista
						ArrayList<TrackBean> sublist = new ArrayList<TrackBean>();
						if(start >= list.size()) 
							start = 0;
						sublist.addAll(list.subList(start, list.size() < start+10 ? list.size() : start+10));
						playlistBean.setTracks(sublist);
						if(currentUser != null)
							for(int i = 0; i < sublist.size() ; i++)
								sublist.get(i).setLiked(userDao.checkIfLiked(currentUser.getId(), sublist.get(i).getId()));
						//Attributi della request
						request.setAttribute("userPage", userBean);
						request.setAttribute("playlist", playlistBean);
						request.setAttribute("number", size);
						request.setAttribute("begin", start);
						request.setAttribute("jspPath", response.encodeURL("/playlist.jsp"));
						request.setAttribute("pageTitle", playlistBean.getName());
						//Forward
						RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/_blank.jsp"));
						rd.forward(request, response);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
