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
import com.heaplay.model.beans.OwnedTrackBean;
import com.heaplay.model.beans.PurchasableTrackBean;
import com.heaplay.model.beans.TrackBean;

public class OwnedTrackDao implements DaoModel {

	private static final String TABLE_NAME = "owns";
	private ConnectionPool pool = null;
	
	public OwnedTrackDao(ConnectionPool pool) {
		this.pool = pool;
	}
	
	@Override
	public synchronized void doSave(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		OwnedTrackBean owBean = (OwnedTrackBean) bean;
		
		String insertQuery = "INSERT INTO " + TABLE_NAME + " (user_id, purchase_date,track_id) VALUES (?, ?,?)";
		
//		PurchasableTrackDao pDao = new PurchasableTrackDao(pool);
//		pDao.doSave(owBean);
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(insertQuery);
			
			ps.setLong(1, owBean.getUserId());
			ps.setTimestamp(2, owBean.getPurchaseDate());
			ps.setLong(3, owBean.getId());
			
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
		
		String updateQuery = "UPDATE "+ TABLE_NAME + "SET purchase_date=? WHERE user_id=? AND track_id=?";
		PurchasableTrackDao pDao = new PurchasableTrackDao(pool);
		pDao.doUpdate(bean);
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(updateQuery);
			
			OwnedTrackBean ownedBean = (OwnedTrackBean) bean;
			
			ps.setTimestamp(1, ownedBean.getPurchaseDate());
			ps.setLong(2, ownedBean.getUserId());
			ps.setLong(2, ownedBean.getId());
			
			int result = ps.executeUpdate();

			if (result != 0)
				con.commit();
		} finally {
			try {
				if (ps != null)
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
		String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE user_id=? AND track_id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(deleteQuery);
			
			ps.setLong(1, Long.parseLong(keys.get(1)));
			ps.setLong(2, Long.parseLong(keys.get(0)));
			
			result = ps.executeUpdate();
			
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		return result != 0;
	}

	@Override
	public synchronized Bean doRetrieveByKey(List<String> keys) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		OwnedTrackBean bean = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE track_id=? AND user_id=?";
		PurchasableTrackDao pDao = new PurchasableTrackDao(pool);
		PurchasableTrackBean purchasableBean = (PurchasableTrackBean) pDao.doRetrieveByKey(keys.subList(0, 1));
		if(purchasableBean == null) {
			TrackDao trackDao = new TrackDao(pool);
			purchasableBean = new PurchasableTrackBean((TrackBean) trackDao.doRetrieveByKey(keys.subList(0, 1)));
		}
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			ps.setLong(1, purchasableBean.getId());
			ps.setLong(2, Long.parseLong(keys.get(1)));

			rs = ps.executeQuery();
			
			if (rs.next()) {
				bean = new OwnedTrackBean(purchasableBean);
				bean.setUserId(rs.getLong("user_id"));
				bean.setPurchaseDate(rs.getTimestamp("purchase_date"));
			}

		} finally {
			try {
				if (ps != null)
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
		ArrayList<Bean> list = new ArrayList<Bean>();
		ArrayList<Bean> listPurchasableTrackBean = new ArrayList<Bean>();
		OwnedTrackBean bean = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		TrackDao trackDao = new TrackDao(pool);
		listPurchasableTrackBean = (ArrayList<Bean>) trackDao.doRetrieveAll(null);
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			rs = ps.executeQuery();
			
			while (rs.next()) {
				for(Bean b : listPurchasableTrackBean) {
					PurchasableTrackBean trackBean = new PurchasableTrackBean( (TrackBean) b);
					if(Long.parseLong(b.getKey().get(0)) == rs.getLong("track_id")) { 
						bean = new OwnedTrackBean(trackBean);
						bean.setUserId(rs.getLong("user_id"));
						bean.setPurchaseDate(rs.getTimestamp("purchase_date"));
						list.add(bean);
						break;
					}	
				}
			}
			if(comparator != null)
				list.sort(comparator);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		return list;
	}

	public synchronized List<TrackBean> getOwnedTrackByUser(Long id,int begin,int end) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		ArrayList<TrackBean> list = new ArrayList<TrackBean>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME +",tracks WHERE user_id=? AND track_id=id  GROUP BY tracks.id LIMIT "+begin+","+end;
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			ps.setLong(1, id);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				TrackBean bean = new TrackBean();
				bean.setId(rs.getLong("id"));
				bean.setAuthor(rs.getLong("author"));
				bean.setImage(rs.getBytes("image"));
				bean.setImageExt(rs.getString("image_extension"));
				bean.setIndexable(rs.getBoolean("indexable"));
				bean.setLikes(rs.getLong("likes"));
				bean.setName(rs.getString("name"));
				bean.setPlays(rs.getLong("plays"));
				bean.setTrack(rs.getBytes("track"));
				bean.setTrackExt(rs.getString("track_extension"));
				bean.setType(rs.getString("type"));
				bean.setUploadDate(rs.getTimestamp("upload_date"));
				//Non ho preso i tag
				bean.setDuration(rs.getInt("duration"));
				bean.setAuthorName(rs.getString("author_name"));
				OwnedTrackBean owned = new OwnedTrackBean(new PurchasableTrackBean(bean));
				owned.setUserId(id);
				owned.setPurchaseDate(rs.getTimestamp("purchase_date"));
				list.add(owned);
			}
				
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		return list;
	}

	public synchronized int getNumberOfTrackByUser(Long id) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		int number = 0;
		
		String selectQuery = "SELECT count(*) FROM " + TABLE_NAME +",tracks WHERE user_id=? AND track_id=id ";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			ps.setLong(1, id);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				number = rs.getInt(1);
			}
				
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		return number;
	}
}
