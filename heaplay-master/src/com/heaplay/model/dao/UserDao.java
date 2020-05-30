package com.heaplay.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.Bean;
import com.heaplay.model.beans.UserBean;

public class UserDao implements DaoModel {

	private static final String TABLE_NAME="users";
	private ConnectionPool pool = null;
	
	
	public UserDao(ConnectionPool pool) {
		this.pool = pool;
	
	}
	
	@Override
	public synchronized void doSave(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		
		String insertQuery = "INSERT INTO " + TABLE_NAME + " (username,email,password,auth,active) VALUES (?,?,MD5(?),?,?)";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(insertQuery);
			UserBean userBean = (UserBean) bean;

			ps.setString(1, userBean.getUsername());
			ps.setString(2, userBean.getEmail());
			ps.setString(3, userBean.getPassword());
			ps.setString(4, userBean.getAuth());
			ps.setBoolean(5, userBean.isActive());
			
			int result = ps.executeUpdate();
			
			if(result != 0)
				con.commit();
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
	}

	@Override
	public synchronized void doUpdate(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET email=?,username=?,password=?,auth=?,active=?,image=?,image_ext=?  WHERE id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(updateQuery);
			UserBean userBean = (UserBean) bean;
			
			ps.setString(1, userBean.getEmail());
			ps.setString(2, userBean.getUsername());
			ps.setString(3, userBean.getPassword());
			ps.setString(4, userBean.getAuth());
			ps.setBoolean(5, userBean.isActive());
			ps.setBytes(6, userBean.getUserImage());
			ps.setString(7, userBean.getUserImageExt());
			ps.setLong(8, userBean.getId());
	
			int result = ps.executeUpdate();
			
			if(result != 0)
				con.commit();
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		
	}
	
	@Override
	public synchronized boolean doDelete(List<String> keys) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		int result = 0;
		String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(deleteQuery);
		
			ps.setLong(1, Long.parseLong(keys.get(0)));
			
			result = ps.executeUpdate();
			con.commit();
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		return (result != 0);
	}

	@Override
	public synchronized UserBean doRetrieveByKey(List<String> keys) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		UserBean bean = null;
		String selectQuery = (keys.size() == 2) ? "SELECT * FROM " + TABLE_NAME + " WHERE email=? and password=MD5(?)" : "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
		
			ps.setString(1, keys.get(0));
			if(keys.size() == 2)
				ps.setString(2, keys.get(1));
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				bean = new UserBean();
				bean.setUsername(rs.getString("username"));
				bean.setId(rs.getLong("id"));
				bean.setEmail(rs.getString("email"));
				bean.setAuth(rs.getString("auth"));
				bean.setPassword(rs.getString("password"));
				bean.setActive(rs.getBoolean("active"));
				bean.setUserImageExt(rs.getString("image_ext"));
			}
			
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		
		return bean;			
	}
	
	public synchronized UserBean doRetrieveByName(String name) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		UserBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE username=? ";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
		
			ps.setString(1, name);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				bean = new UserBean();
				bean.setUsername(rs.getString("username"));
				bean.setId(rs.getLong("id"));
				bean.setEmail(rs.getString("email"));
				bean.setAuth(rs.getString("auth"));
				bean.setActive(rs.getBoolean("active"));
				bean.setUserImageExt(rs.getString("image_ext"));
			}
			
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		
		return bean;			
	}
	
	@Override
	public synchronized List<Bean> doRetrieveAll(Comparator<Bean> comparator) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		List<Bean> list =  new ArrayList<Bean>(); 
		UserBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new UserBean();
				bean.setUsername(rs.getString("username"));
				bean.setId(rs.getLong("id"));
				bean.setEmail(rs.getString("email"));
				bean.setAuth(rs.getString("auth"));
				bean.setPassword(rs.getString("password"));
				bean.setActive(rs.getBoolean("active"));
				bean.setUserImageExt(rs.getString("image_ext"));
				list.add(bean);
			}
			
			if(comparator != null)
				list.sort(comparator);
			
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		
		return list;
	}
	
	public synchronized byte[] getImage(Long id) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		byte[] image = null;
		String selectQuery = "SELECT image FROM " + TABLE_NAME + " WHERE id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
		
			ps.setLong(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) 
				image = rs.getBytes(1);
			
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		
		return image;			
	}
	
	public synchronized long getNumbersOfUsers() throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		long number = 0;
		String selectQuery = "SELECT count(*) FROM " + TABLE_NAME + " WHERE auth='user' AND active='1'";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			
			rs = ps.executeQuery();
			
			if(rs.next()) 
				number = rs.getLong(1);
			
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		
		return number;			
	}
	
	
	public synchronized void saveLike(long userId,long trackId) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		String selectQuery = "INSERT INTO liked(user_id,track_id) VALUES(?,?)";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			
			ps.setLong(1, userId);
			ps.setLong(2, trackId);
			
			ps.executeUpdate();
			
			con.commit();
			
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
				
	}
	
	public synchronized void removeLike(long userId,long trackId) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		String removeQuery = "DELETE FROM liked WHERE user_id=? AND track_id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(removeQuery);
			
			ps.setLong(1, userId);
			ps.setLong(2, trackId);
			
			ps.executeUpdate();
			
			con.commit();
			
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
				
	}
	
	public synchronized boolean checkIfLiked(long userId,long trackId) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		boolean flag = false;
		String selectQuery = "SELECT * FROM liked WHERE track_id=? AND user_id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			ps.setLong(1, trackId);
			ps.setLong(2, userId);
			
			rs = ps.executeQuery();
			
			
			if(rs.next()) 
				flag = true;
			
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		
		return flag;			
	}

}
