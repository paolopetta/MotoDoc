package com.heaplay.model.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO della Track table
 * 
 */
public class TrackBean extends Bean implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String type;
	private long plays;
	private int duration;
	private byte[] track;
	private String trackExt;
	private byte[] image;
	private String imageExt;
	private Timestamp uploadDate;
	private long likes;
	private boolean indexable;
	private long author;
	private String authorName;
	private List<TagBean> tags;
	private boolean liked;
	
	public TrackBean() {
		this.id = this.author = -1;
		this.plays = this.likes = this.duration = 0;
		this.name = this.type = this.trackExt = this.imageExt = this.authorName = "";
		this.track = null;
		this.image = null;
		this.uploadDate = null;
		this.indexable = this.liked = false;
		this.tags = new ArrayList<TagBean>();
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public long getPlays() {
		return plays;
	}
	
	public void setPlays(long plays) {
		this.plays = plays;
	}
	
	public byte[] getTrack() {
		return track;
	}
	
	public void setTrack(byte[] track) {
		this.track = track;
	}
	
	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}
	
	public String getTrackExt() {
		return trackExt;
	}
	
	public void setTrackExt(String trackExt) {
		this.trackExt = trackExt;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public String getImageExt() {
		return imageExt;
	}
	
	public void setImageExt(String imageExt) {
		this.imageExt = imageExt;
	}
	
	public Timestamp getUploadDate() {
		return (Timestamp) uploadDate.clone();
	}
	
	public void setUploadDate( Timestamp uploadDate) {
		this.uploadDate =  (Timestamp) uploadDate.clone();
	}
	
	public long getLikes() {
		return likes;
	}
	
	public void setLikes(long likes) {
		this.likes = likes;
	}
	
	public boolean isIndexable() {
		return indexable;
	}
	
	public void setIndexable(boolean indexable) {
		this.indexable = indexable;
	}
	
	public long getAuthor() {
		return author;
	}
	
	public void setAuthor(long author) {
		this.author = author;
	}
	
	public String getAuthorName() {
		return authorName;
	}
	
	public void setAuthorName(String name) {
		this.authorName = name;
	}
	
	public List<TagBean> getTags() {
		return tags;
	}

	public void setTags(List<TagBean> tags) {
		this.tags = tags;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public boolean equals(Object otherOb) {
		if(otherOb == null || otherOb.getClass() != getClass())
			return false;
		TrackBean other = (TrackBean) otherOb;
		return other.id == id;
	}

	@Override
	public TrackBean clone() {
		TrackBean bean = null;
		try {
			bean = (TrackBean) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return bean;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", name=" + name + ", type=" + type + ", plays=" + plays + ", trackExt="
				+ trackExt + ", imageExt=" + imageExt + ", uploadDate=" + uploadDate + ", likes=" + likes
				+ ", indexable=" + indexable + ", author=" + author + "tags="+tags +"]";
	}

	/**
	 * Ritorna (this.id)
	 */	
	@Override
	public List<String> getKey() {
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(String.valueOf(id));
		return keys;
	}

	/**
	 * Ritorna this.id - otherBean.id
	 */
	@Override
	public int compareKey(Bean otherBean) {
		if(this.getClass() != otherBean.getClass())
			return 1;
		TrackBean other = (TrackBean) otherBean;
		return (int) (id - other.id);
	}

	@Override
	public String getBeanName() {
		return name;
	}
}
