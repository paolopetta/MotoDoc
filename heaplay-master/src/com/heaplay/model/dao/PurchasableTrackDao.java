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
import com.heaplay.model.beans.PurchasableTrackBean;
import com.heaplay.model.beans.TrackBean;

public class PurchasableTrackDao implements DaoModel {

	private static final String TABLE_NAME = "purchasable_tracks";
	
	private ConnectionPool pool = null;

	public PurchasableTrackDao(ConnectionPool pool) {
		this.pool = pool;
	}
	
	@Override
	public synchronized void doSave(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		
		String insertQuery = "INSERT INTO "+ TABLE_NAME + " (id,sold,price) VALUES (?,?,?)";
		TrackDao tDao = new TrackDao(pool);
		tDao.doSave(bean);
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(insertQuery);
			
			PurchasableTrackBean ptBean = (PurchasableTrackBean) bean;
			
			ps.setLong(1,ptBean.getId());
			ps.setLong(2, ptBean.getSold());
			ps.setDouble(3, ptBean.getPrice());
			
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
	public synchronized void doUpdate(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		
		String updateQuery = "UPDATE "+ TABLE_NAME + " SET sold=? , price=? WHERE id=?";
		TrackDao tDao = new TrackDao(pool);
		tDao.doUpdate(bean);
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(updateQuery);
			
			PurchasableTrackBean ptBean = (PurchasableTrackBean) bean;
			
			ps.setLong(1, ptBean.getSold());
			ps.setDouble(2, ptBean.getPrice());
			ps.setLong(3, ptBean.getId());
			
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
		TrackDao tDao = new TrackDao(pool);
		return tDao.doDelete(keys);
	}

	@Override
	public synchronized Bean doRetrieveByKey(List<String> keys) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		PurchasableTrackBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
		TrackDao tDao = new TrackDao(pool);
		TrackBean trackBean = (TrackBean) tDao.doRetrieveByKey(keys);
		if(trackBean == null)
			return trackBean;
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			ps.setLong(1, trackBean.getId());

			rs = ps.executeQuery();
			
			if (rs.next()) {
				bean = new PurchasableTrackBean(trackBean);
				bean.setSold(rs.getLong("sold"));
				bean.setPrice(rs.getDouble("price"));
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
		ArrayList<Bean> listTrackBean = new ArrayList<Bean>();
		PurchasableTrackBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		TrackDao tDao = new TrackDao(pool);
		listTrackBean = (ArrayList<Bean>) tDao.doRetrieveAll(null);
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			rs = ps.executeQuery();
			
			while (rs.next()) {
				for(Bean b : listTrackBean) {
					TrackBean trackBean = (TrackBean) b;
					if(Long.parseLong(b.getKey().get(0)) == rs.getLong("id")) { 
						bean = new PurchasableTrackBean(trackBean);
						bean.setSold(rs.getLong("sold"));
						bean.setPrice(rs.getDouble("price"));
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

	public synchronized ArrayList<TrackBean> getMostSoldTracks() throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		ArrayList<TrackBean> list = new ArrayList<TrackBean>();
		ArrayList<Bean> listTrackBean = new ArrayList<Bean>();
		PurchasableTrackBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY sold DESC";
		TrackDao tDao = new TrackDao(pool);
		listTrackBean = (ArrayList<Bean>) tDao.doRetrieveAll(null);
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			rs = ps.executeQuery();
			
			while (rs.next()) {
				for(Bean b : listTrackBean) {
					TrackBean trackBean = (TrackBean) b;
					if(trackBean.getId() == rs.getLong("id") && trackBean.isIndexable()) { 
						bean = new PurchasableTrackBean(trackBean);
						bean.setSold(rs.getLong("sold"));
						bean.setPrice(rs.getDouble("price"));
						list.add(bean);
						break;
					}
					if(list.size() == 5)
						break;
				}
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
}
