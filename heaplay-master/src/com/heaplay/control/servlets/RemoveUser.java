package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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


@WebServlet("/admin/removeUser")
public class RemoveUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lettura parametri
		String user_id = request.getParameter("user_id");
		String disable = request.getParameter("disable");
		String enable  = request.getParameter("enable");
		
		if(user_id == null)
			response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/home"));
		else {
			//Dao
			ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
			UserDao userDao = new UserDao(pool);
			TrackDao trackDao = new TrackDao(pool);
			
			try {
				//Track dell'utente
				ArrayList<TrackBean> list = trackDao.getTracksByAuthor(Long.parseLong(user_id), -1, -1,"all");
				
				if(enable == null) {
					boolean flag = false;
					
					for (int i = 0 ; i < list.size(); i++)
						if(disable != null || list.get(i).getType().equals("pagamento")) {
							//Disabilità la track
							flag=true;
							list.get(i).setIndexable(false);
							trackDao.doUpdate(list.get(i));
						} else 
							//Elimina la track
							trackDao.doDelete(list.get(i).getKey());
					ArrayList<String> keys = new ArrayList<String>();
					keys.add(user_id);
					UserBean userBean = userDao.doRetrieveByKey(keys);
					if(disable == null && !flag) 
						//Elimina l'utente
						userDao.doDelete(keys);
					else {
						//Disabilita l'utente
						userBean.setActive(false);
						userDao.doUpdate(userBean);
					}
				} else {
					//Abilita le track e l'utente
					for (int i = 0 ; i < list.size(); i++) {
							list.get(i).setIndexable(true);
							trackDao.doUpdate(list.get(i));
						} 
					ArrayList<String> keys = new ArrayList<String>();
					keys.add(user_id);
					UserBean userBean = userDao.doRetrieveByKey(keys);
					userBean.setActive(true);
					userDao.doUpdate(userBean);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

}
