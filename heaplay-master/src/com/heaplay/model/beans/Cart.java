package com.heaplay.model.beans;

import java.util.ArrayList;
import java.util.List;

public class Cart<T> {
	private ArrayList<T> cart;
	
	public Cart () {
		cart = new ArrayList<T>();
	}

	public void addItem(T item) {
		cart.add(item);
	}
	
	public void deleteItem(T item) {
		cart.remove(item);
	}
	
	public List<T> getItems() {
		return cart;
	}
	
	public void setItems (ArrayList<T> items) {
		cart = items;
	}
	
	public void deleteAll() {
		cart.clear();
	}

}
