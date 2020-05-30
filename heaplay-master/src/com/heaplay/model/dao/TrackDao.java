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
import com.heaplay.model.beans.TrackBean;

public class TrackDao implements DaoModel {

	private static final String TABLE_NAME = "tracks";
	private ConnectionPool pool = null;

	public TrackDao(ConnectionPool pool) {
		this.pool = pool;
	}

	@Override
	public synchronized void doSave(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;

		String insertQuery = "INSERT INTO " + TABLE_NAME
				+ " (name,type,plays,track,track_extension,image,image_extension,indexable,author,upload_date,likes,duration,author_name) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String insertTags = "INSERT INTO  tagged(track_id,tag_id) VALUES (?,?) ";
		String getId = "SELECT id FROM "+ TABLE_NAME + " WHERE name= ? AND author=? ORDER BY upload_date DESC";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(insertQuery);
			TrackBean trackBean = (TrackBean) bean;

			ps.setString(1, trackBean.getName());
			ps.setString(2, trackBean.getType());
			ps.setLong(3, trackBean.getPlays());
			ps.setBytes(4, trackBean.getTrack());
			ps.setString(5, trackBean.getTrackExt());
			ps.setBytes(6, trackBean.getImage());
			ps.setString(7, trackBean.getImageExt());
			ps.setBoolean(8, trackBean.isIndexable());
			ps.setLong(9, trackBean.getAuthor());
			ps.setTimestamp(10,trackBean.getUploadDate());
			ps.setLong(11, trackBean.getLikes());
			ps.setInt(12, trackBean.getDuration());
			ps.setString(13, trackBean.getAuthorName());
			int result = ps.executeUpdate();
			
			con.commit(); 
			
			if (ps != null)
				ps.close();
			
			ps = con.prepareStatement(getId);
			ps.setString(1, trackBean.getName());
			ps.setLong(2, trackBean.getAuthor());
			ResultSet rs = ps.executeQuery();
			rs.next();
			Long id = rs.getLong("id");
			trackBean.setId(id);
			
			if (ps != null)
				ps.close();
			
			ps = con.prepareStatement(insertTags);
			
			ArrayList<TagBean> list = (ArrayList<TagBean>) trackBean.getTags();
			for (int i = 0 ; i < list.size(); i++) {							//Con il foreach faceva due esecuzioni con un solo bean?!
				TagBean tag = list.get(i);
				ps.clearParameters();
				ps.setLong(1, trackBean.getId());
				if(tag.getId() != -1)
					ps.setLong(2, tag.getId());
				else {
					TagDao tagDao = new TagDao(pool);
					ArrayList<String> keys = new ArrayList<String>();
					keys.add(tag.getId()+"");
					keys.add(tag.getName());
					TagBean tagBean=(TagBean) tagDao.doRetrieveByKey(keys);		//Controllo se il tag gi� esiste
					if(tagBean == null) {
						tagDao.doSave(tag);
						con.commit();
						tagBean=(TagBean) tagDao.doRetrieveByKey(keys);
					}
					ps.setLong(2, tagBean.getId());
				}
				ps.executeUpdate();
			}

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

		String updateQuery = "UPDATE " + TABLE_NAME
				+ " SET  name=?,type=?,plays=?,indexable=?,author=?,upload_date=?,likes=?,duration=?,author_name=? WHERE id=?";
		String insertTagged = "INSERT INTO  tagged(track_id,tag_id) VALUES (?,?) ";
		String deleteTagged = "DELETE FROM tagged WHERE track_id=? AND tag_id=?";

		try {
			con = pool.getConnection();
			ps = con.prepareStatement(updateQuery);
			TrackBean trackBean = (TrackBean) bean;

			ps.setString(1, trackBean.getName());
			ps.setString(2, trackBean.getType());
			ps.setLong(3, trackBean.getPlays());
			ps.setBoolean(4, trackBean.isIndexable());
			ps.setLong(5, trackBean.getAuthor());
			ps.setTimestamp(6, trackBean.getUploadDate());
			ps.setLong(7, trackBean.getLikes());
			ps.setLong(8, trackBean.getDuration());
			ps.setString(9, trackBean.getAuthorName());
			ps.setLong(10, trackBean.getId());
			

			int result = ps.executeUpdate();

			if (ps != null)
				ps.close();
			TagDao tagDao = new TagDao(pool);
			ArrayList<TagBean> tags = tagDao.getTagsByTrack(trackBean.getId());
			ps = con.prepareStatement(insertTagged);

			for (TagBean tag : trackBean.getTags()) {
				if (!tags.contains(tag)) {
					ps.clearParameters();
					ps.setLong(1, trackBean.getId());
					ps.setLong(2, tag.getId());
					ps.executeUpdate();
				} /*else {
					ps.clearParameters();
					if(!tags.get(tags.indexOf(tag)).getName().equals(tag.getName()))		// Controllare se � utile!!!
						tagDao.doUpdate(tag);
				}*/
			}
			if (ps != null)
				ps.close();
			ps = con.prepareStatement(deleteTagged);
			tags.removeAll(trackBean.getTags());
			if (tags.size() != 0) {
				for (TagBean tag : tags) {
					ps.clearParameters();
					ps.setLong(1, trackBean.getId());
					ps.setLong(2, tag.getId());
					ps.executeUpdate();
				}
			}

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
		String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE id=?";

		try {
			con = pool.getConnection();
			ps = con.prepareStatement(deleteQuery);
			ps.setLong(1, Long.parseLong(keys.get(0)));
			result = ps.executeUpdate();
			con.commit();
		} finally {
			try {
				if (ps != null)
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
		TrackBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";

		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			ps.setLong(1, Long.parseLong(keys.get(0)));

			rs = ps.executeQuery();
			TagDao tag = new TagDao(pool);

			if (rs.next()) {
				bean = new TrackBean();
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
				bean.setTags(tag.getTagsByTrack(rs.getLong("id")));
				bean.setDuration(rs.getInt("duration"));
				bean.setAuthorName(rs.getString("author_name"));
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
		TrackBean bean = null;
		String selectQuery = "SELECT id,author,image_extension,indexable,likes,name,plays,track_extension,type,upload_date,duration,author_name FROM " + TABLE_NAME;
		ArrayList<Bean> list = new ArrayList<Bean>();

		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			rs = ps.executeQuery();
//			TagDao tag = new TagDao(pool);

			while (rs.next()) {
				bean = new TrackBean();
				bean.setId(rs.getLong("id"));
				bean.setAuthor(rs.getLong("author"));
//				bean.setImage(rs.getBytes("image"));
				bean.setImageExt(rs.getString("image_extension"));
				bean.setIndexable(rs.getBoolean("indexable"));
				bean.setLikes(rs.getLong("likes"));
				bean.setName(rs.getString("name"));
				bean.setPlays(rs.getLong("plays"));
//				bean.setTrack(rs.getBytes("track"));
				bean.setTrackExt(rs.getString("track_extension"));
				bean.setType(rs.getString("type"));
				bean.setUploadDate(rs.getTimestamp("upload_date"));
//				bean.setTags(tag.getTagsByTrack(rs.getLong("id")));
				bean.setDuration(rs.getInt("duration"));
				bean.setAuthorName(rs.getString("author_name"));
				list.add(bean);
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

	public synchronized ArrayList<TrackBean> getTracksByPlaylist(Long id) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		TrackBean bean = null;
		String selectQuery = "SELECT id,author,image_extension,indexable,likes,name,plays,track_extension,type,upload_date,duration,author_name FROM " + TABLE_NAME + " , contains WHERE playlist_id=? AND track_id=id";
		ArrayList<TrackBean> list = new ArrayList<TrackBean>();

		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			TagDao tag = new TagDao(pool);

			while (rs.next()) {
				bean = new TrackBean();
				bean.setId(rs.getLong("id"));
				bean.setAuthor(rs.getLong("author"));
//				bean.setImage(rs.getBytes("image"));
				bean.setImageExt(rs.getString("image_extension"));
				bean.setIndexable(rs.getBoolean("indexable"));
				bean.setLikes(rs.getLong("likes"));
				bean.setName(rs.getString("name"));
				bean.setPlays(rs.getLong("plays"));
//				bean.setTrack(rs.getBytes("track"));
				bean.setTrackExt(rs.getString("track_extension"));
				bean.setType(rs.getString("type"));
				bean.setUploadDate(rs.getTimestamp("upload_date"));
				bean.setTags(tag.getTagsByTrack(rs.getLong("id")));
				bean.setDuration(rs.getInt("duration"));
				bean.setAuthorName(rs.getString("author_name"));
				list.add(bean);
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
	
	public synchronized byte[] getImage(Long id) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		String selectQuery = "SELECT image FROM " + TABLE_NAME + " WHERE id=?";
		byte [] image = null;
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			ps.setLong(1, id);
			rs = ps.executeQuery();

			if (rs.next())
				image = rs.getBytes("image");				
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		return image;
	}
	
	public synchronized byte[] getAudio(Long id) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		String selectQuery = "SELECT track FROM " + TABLE_NAME + " WHERE id=?";
		byte [] audio = null;
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			ps.setLong(1, id);
			rs = ps.executeQuery();

			if (rs.next())
				audio = rs.getBytes("track");				
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		return audio;
	}
	

	public synchronized List<TrackBean> doRetrieveByName(String name) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		TrackBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE name=?";
		ArrayList<TrackBean> list = new ArrayList<TrackBean>();

		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			ps.setString(1, name);
			rs = ps.executeQuery();
			TagDao tag = new TagDao(pool);

			while (rs.next()) {
				bean = new TrackBean();
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
				bean.setTags(tag.getTagsByTrack(rs.getLong("id")));
				bean.setDuration(rs.getInt("duration"));
				bean.setAuthorName(rs.getString("author_name"));
				list.add(bean);
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
	
	public synchronized ArrayList<TrackBean> getTracksByAuthor(Long id,int begin,int end,String all) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		TrackBean bean = null;
		String allSelection =  (!all.equals("all")) ?   "AND indexable='1'" : "";
		String selectQuery = (begin!=-1 && end != -1) ? "SELECT * FROM " + TABLE_NAME + " WHERE author=? AND indexable='1' GROUP BY id LIMIT "+begin+","+end : "SELECT * FROM " + TABLE_NAME + " WHERE author=? "+ allSelection ;
		ArrayList<TrackBean> list = new ArrayList<TrackBean>();

		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			TagDao tag = new TagDao(pool);

			while (rs.next()) {
				bean = new TrackBean();
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
				bean.setTags(tag.getTagsByTrack(rs.getLong("id")));
				bean.setDuration(rs.getInt("duration"));
				bean.setAuthorName(rs.getString("author_name"));
				list.add(bean);
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
	
	public synchronized int getNumberOfTracksOfAuthor(Long id) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		String selectQuery = "SELECT count(*) FROM " + TABLE_NAME + " WHERE author=? AND indexable='1'";
		int n = 0;
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if(rs.next())
				n=rs.getInt(1);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		return n;
	}
	
	public synchronized ArrayList<Bean> getTracksByTag(Long id) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Bean> list = new ArrayList<Bean>();
		TrackBean bean = null;
		String selectQuery = "SELECT id,author,image_extension,indexable,likes,name,plays,track_extension,type,upload_date,duration,author_name FROM " + TABLE_NAME + ",tagged WHERE track_id=id AND tag_id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			TagDao tag = new TagDao(pool);

			while (rs.next()) {
				bean = new TrackBean();
				bean.setId(rs.getLong("id"));
				bean.setAuthor(rs.getLong("author"));
//				bean.setImage(rs.getBytes("image"));
				bean.setImageExt(rs.getString("image_extension"));
				bean.setIndexable(rs.getBoolean("indexable"));
				bean.setLikes(rs.getLong("likes"));
				bean.setName(rs.getString("name"));
				bean.setPlays(rs.getLong("plays"));
//				bean.setTrack(rs.getBytes("track"));
				bean.setTrackExt(rs.getString("track_extension"));
				bean.setType(rs.getString("type"));
				bean.setUploadDate(rs.getTimestamp("upload_date"));
				bean.setTags(tag.getTagsByTrack(rs.getLong("id")));
				bean.setDuration(rs.getInt("duration"));
				bean.setAuthorName(rs.getString("author_name"));
				list.add(bean);
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
	
	public synchronized ArrayList<TrackBean> getCart(Long id) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		ArrayList<TrackBean> list = new ArrayList<TrackBean>();
		TrackBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME + ",carted WHERE track_id ="+TABLE_NAME +".id AND user_id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			TagDao tag = new TagDao(pool);

			while (rs.next()) {
				bean = new TrackBean();
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
				bean.setTags(tag.getTagsByTrack(rs.getLong("id")));
				bean.setDuration(rs.getInt("duration"));
				bean.setAuthorName(rs.getString("author_name"));
				if(bean.getType().equals("pagamento")) {
					PurchasableTrackDao purchasableTrackDao = new PurchasableTrackDao(pool);
					bean = (TrackBean) purchasableTrackDao.doRetrieveByKey(bean.getKey());
				}
				list.add(bean);
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
	
	public synchronized void saveCart(List<TrackBean> cart,Long id) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		ArrayList<TrackBean> prevCart = new ArrayList<TrackBean>();
		TrackBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME + ",carted WHERE track_id ="+TABLE_NAME +".id AND user_id=?";
		String insertQuery = "INSERT INTO carted(track_id,user_id) VALUES(?,?) ";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			ps.setLong(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TrackBean();
				bean.setId(rs.getLong("id"));
				prevCart.add(bean);
			}
		
			for(int i = 0; i < cart.size() ; i++) {
				boolean found = false;
				for(int j = 0;!found && j < prevCart.size() ; j++)
					if(prevCart.get(j).getId() == cart.get(i).getId() ) { 
						prevCart.remove(j);
						found = true;
					}
				
				if(found == false ) {
					ps = con.prepareStatement(insertQuery);
					ps.setLong(1,cart.get(i).getId());
					ps.setLong(2,id);
					ps.executeUpdate();
					ps.close();
				}
			}
			
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
	
	public synchronized void updateCart(List<TrackBean> cart,Long id) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		ArrayList<TrackBean> prevCart = new ArrayList<TrackBean>();
		TrackBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME + ",carted WHERE track_id ="+TABLE_NAME +".id AND user_id=?";
		String removeQuery = "DELETE FROM carted WHERE track_id=? AND user_id=?";
		
		try {
			doRetrieveByName("");	//Query inutile per appesantire il DB 
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			ps.setLong(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TrackBean();
				bean.setId(rs.getLong("id"));
				prevCart.add(bean);
			}
		
			for(int i = 0; i < cart.size() ; i++) {
				boolean found = false;
				for(int j = 0;!found && j < prevCart.size() ; j++)
					if(prevCart.get(j).getId() == cart.get(i).getId() ) { 
						prevCart.remove(j);
						found = true;
					}
			}
			
			for(int i = 0; i < prevCart.size() ; i++) {
				ps = con.prepareStatement(removeQuery);
				ps.setLong(1, prevCart.get(i).getId());
				ps.setLong(2,id);
				ps.executeUpdate();
				ps.close();
			}
			
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
	
	
	public synchronized long getNumberOfTracks() throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		String selectQuery = "SELECT count(*) FROM " + TABLE_NAME + " WHERE indexable='1'";
		long n = 0;
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			rs = ps.executeQuery();
			if(rs.next())
				n=rs.getLong(1);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		return n;
	}
	
	public synchronized ArrayList<TrackBean> getTracksByParameter(String param,int begin,int end) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		TrackBean bean = null;
		String selectQuery ="SELECT id,author,image_extension,indexable,likes,name,plays,track_extension,type,upload_date,duration,author_name FROM " + TABLE_NAME + " WHERE indexable='1' GROUP BY id ORDER BY "+param+" DESC LIMIT "+begin+","+end;
		ArrayList<TrackBean> list = new ArrayList<TrackBean>();

		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			rs = ps.executeQuery();
			TagDao tag = new TagDao(pool);

			while (rs.next()) {
				bean = new TrackBean();
				bean.setId(rs.getLong("id"));
				bean.setAuthor(rs.getLong("author"));
//				bean.setImage(rs.getBytes("image"));
				bean.setImageExt(rs.getString("image_extension"));
				bean.setIndexable(rs.getBoolean("indexable"));
				bean.setLikes(rs.getLong("likes"));
				bean.setName(rs.getString("name"));
				bean.setPlays(rs.getLong("plays"));
//				bean.setTrack(rs.getBytes("track"));
				bean.setTrackExt(rs.getString("track_extension"));
				bean.setType(rs.getString("type"));
				bean.setUploadDate(rs.getTimestamp("upload_date"));
				bean.setTags(tag.getTagsByTrack(rs.getLong("id")));
				bean.setDuration(rs.getInt("duration"));
				bean.setAuthorName(rs.getString("author_name"));
				list.add(bean);
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
