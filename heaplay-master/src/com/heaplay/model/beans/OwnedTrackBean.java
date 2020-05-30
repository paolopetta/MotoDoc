package com.heaplay.model.beans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OwnedTrackBean extends PurchasableTrackBean {
	
	private static final long serialVersionUID = 1L;
	
	private long userId;
	private Timestamp purchaseDate;	
	
	public OwnedTrackBean () {
		super();
		userId = -1;
		purchaseDate = null;
	}
	
	public OwnedTrackBean(PurchasableTrackBean bean) {
		super(bean);
		setSold(bean.getSold());
		setPrice(bean.getPrice());
		
		userId = -1;
		purchaseDate = null;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public Timestamp getPurchaseDate() {
		return purchaseDate;
	}
	
	public void setPurchaseDate(Timestamp purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	@Override
	public boolean equals(Object otherOb) {
		return super.equals(otherOb) && ((OwnedTrackBean)otherOb).userId == userId;
	}
	
	@Override
	public OwnedTrackBean clone() {
		return (OwnedTrackBean) super.clone();
	}

	@Override
	public String toString() {
		return super.toString() + " [userId=" + userId + ", purchaseDate=" + purchaseDate + "]";
	}
	
	/**
	 * Ritorna (this.id, this.userId)
	 */
	public List<String> getKey() {
		ArrayList<String> keys = (ArrayList<String>) super.getKey();
		keys.add(String.valueOf(userId));
		return keys;
	}	
}
 
