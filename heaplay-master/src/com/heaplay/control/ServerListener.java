package com.heaplay.control;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.heaplay.model.ConnectionPool;

@WebListener
public class ServerListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent sce)  { 
		try {
			ConnectionPool pool = (ConnectionPool) sce.getServletContext().getAttribute("pool");
			if (pool != null)
				pool.destroyPool();

			System.out.println("DB Connection Pool successfully destroyed\n");

		} catch (SQLException e) {
			System.out.println("Could not correctely destroy DB Connection Pool\n" + e.getMessage());
		}
	}

    public void contextInitialized(ServletContextEvent sce)  { 
    	try {
			ConnectionPool pool = new ConnectionPool();
			ServletContext context = sce.getServletContext();
			
			context.setAttribute("pool", pool);
			
			System.out.println("DB Connection Pool successfully created");
			
		} catch (SQLException e) {
			System.out.println("Could not create DB Connection Pool\n" + e.getMessage());
		}
    }
	
}
