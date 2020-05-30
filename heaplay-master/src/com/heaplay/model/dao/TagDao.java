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
import com.heaplay.model.beans.TagBean;

public class TagDao implements DaoModel {

	private static final String TABLE_NAME = "tags";
	private ConnectionPool pool = null;
	
	public TagDao(ConnectionPool pool) {
		this.pool = pool;
	}
	
	@Override
	public synchronized void doSave(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		
		String insertQuery = "INSERT INTO " + TABLE_NAME + " (name) VALUES (?)";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(insertQuery);
			TagBean tagBean = (TagBean) bean;
			
			ps.setString(1, tagBean.getName());
			
			if(ps.executeUpdate() != 0) 
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
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET name=?  WHERE id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(updateQuery);
			TagBean tagBean = (TagBean) bean;
			
			ps.setString(1, tagBean.getName());
			ps.setLong(2, Long.parseLong(tagBean.getKey().get(0)));
			
			if(ps.executeUpdate() != 0)
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
	public synchronized Bean doRetrieveByKey(List<String> keys) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		TagBean bean = null;
		String selectQuery = keys.size() == 1 ? "SELECT * FROM " + TABLE_NAME + " WHERE id=?" : "SELECT * FROM " + TABLE_NAME + " WHERE name=?";
		String key = keys.get(keys.size() - 1);
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
		
			ps.setString(1, key);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				bean = new TagBean();
				bean.setId(rs.getLong("id"));
				bean.setName(rs.getString("name"));
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
		TagBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new TagBean();
				bean.setId(rs.getLong("id"));
				bean.setName(rs.getString("name"));
				list.add(bean);
			}
			
//			if(comparator != null)
//				list.sort(comparator);
			
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
	
	public synchronized ArrayList<TagBean> getTagsByTrack(Long id) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		TagBean bean = null;
		ArrayList<TagBean> list =  new ArrayList<TagBean>();
		
		String selectQuery = "SELECT id, name FROM " + TABLE_NAME + ", tagged WHERE track_id = ? AND tag_id = id";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			
			ps.setLong(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new TagBean();
				bean.setId(rs.getLong("id"));
				bean.setName(rs.getString("name"));
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
