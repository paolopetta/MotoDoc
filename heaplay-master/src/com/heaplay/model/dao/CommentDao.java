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
import com.heaplay.model.beans.CommentBean;

public class CommentDao implements DaoModel {
	
	private static final String TABLE_NAME="comments";
	private ConnectionPool pool = null;

	public CommentDao(ConnectionPool pool) {
		this.pool = pool;
	
	}
	
	@Override
	public synchronized void doSave(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		
		String insertQuery = "INSERT INTO " + TABLE_NAME + " (track_id,user_id,body,author) VALUES (?,?,?,?)";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(insertQuery);
			CommentBean commentBean = (CommentBean) bean;

			ps.setLong(1, commentBean.getTrackId());
			ps.setLong(2, commentBean.getUserId());
			ps.setString(3, commentBean.getBody());
			ps.setString(4, commentBean.getAuthor());
			
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
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET body=?  WHERE id=? AND track_id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(updateQuery);
			CommentBean commentBean = (CommentBean) bean;
			
			ps.setString(1, commentBean.getBody());		
			ps.setLong(2, Long.parseLong(commentBean.getKey().get(0)));
			ps.setLong(3, Long.parseLong(commentBean.getKey().get(1)));

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
		String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE id=? AND track_id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(deleteQuery);
		
			ps.setLong(1, Long.parseLong(keys.get(0)));
			ps.setLong(2, Long.parseLong(keys.get(1)));

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
	public synchronized Bean doRetrieveByKey(List<String> keys) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		CommentBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE id=? AND track_id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
		
			ps.setString(1, keys.get(0));
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				bean = new CommentBean();
				bean.setBody(rs.getString("body"));
				bean.setId(rs.getLong("id"));
				bean.setTrackId(rs.getLong("track_id"));
				bean.setUserId(rs.getLong("user_id"));
				bean.setAuthor(rs.getString("author"));
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
		CommentBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new CommentBean();
				bean.setBody(rs.getString("body"));
				bean.setId(rs.getLong("id"));
				bean.setTrackId(rs.getLong("track_id"));
				bean.setUserId(rs.getLong("user_id"));
				bean.setAuthor(rs.getString("author"));
				list.add(bean);
			}
			
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
	
	public synchronized List<Bean> getCommentsByTrack(Long id,int offset,int numberToLoad) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		List<Bean> list =  new ArrayList<Bean>(); 
		CommentBean bean = null;
		String selectQuery = "SELECT DISTINCT * FROM " + TABLE_NAME +",users WHERE track_id=? AND active='1' GROUP BY "+TABLE_NAME +".id LIMIT "+ offset+","+numberToLoad;
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			
			ps.setLong(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new CommentBean();
				bean.setBody(rs.getString("body"));
				bean.setId(rs.getLong("id"));
				bean.setTrackId(rs.getLong("track_id"));
				bean.setUserId(rs.getLong("user_id"));
				bean.setAuthor(rs.getString("author"));
				list.add(bean);
			}
			
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
	
}
