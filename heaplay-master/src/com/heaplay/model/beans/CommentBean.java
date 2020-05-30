package com.heaplay.model.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommentBean extends Bean implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private long id;
	private long userId;
	private long trackId;
	private String author;
	private String body;
	
	public CommentBean() {
		id = userId = trackId = -1;
		body = "";
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getTrackId() {
		return trackId;
	}
	
	public void setTrackId(long trackId) {
		this.trackId = trackId;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String body) {
		this.author = body;
	}
	
	@Override
	public boolean equals(Object otherOb) {
		if(otherOb == null || otherOb.getClass() != getClass())
			return false;
		CommentBean other = (CommentBean) otherOb;
		return other.id == id && other.trackId == trackId && other.userId == userId;
	}

	@Override
	public CommentBean clone() {
		CommentBean bean = null;
		try {
			bean = (CommentBean) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return bean;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", userId=" + userId + ", trackId=" + trackId + ", body=" + body + "]";
	}

	/**
	 * 	Ritorna (this.id, this.trackId)
	 */
	@Override
	public List<String> getKey() {
		ArrayList<String> key = new ArrayList<String>();
		key.add(String.valueOf(id));
		key.add(String.valueOf(trackId));
		return key;
	}

	/**
	 * 	Ritorna 0 se entrambe le chiavi sono uguali,
	 * 	1 se this.trackId > otherBean.trackId oppure se a parita' di trackId, id > otherBean.id,
	 * 	-1 se this.trackId < otherBean.trackId oppure se a parita' di trackId, id < otherBean.id
	 */
	@Override
	public int compareKey(Bean otherBean) {
		if(this.getClass() != otherBean.getClass())
			return 1;
		
		CommentBean other = (CommentBean) otherBean;
		if (trackId < other.trackId)
			return -1;
		if (trackId > other.trackId)
			return 1;
		else
			return (int) (id - other.id);
	}

	@Override
	public String getBeanName() {
		return null;
	}
}
