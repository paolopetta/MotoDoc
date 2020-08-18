package it.unisa.model;

import java.util.ArrayList;
import java.util.List;

public class Cart<T> {
    List<T> items;

    public Cart() {
        items = new ArrayList<T>();
    }

    public void addItem(T item) {
        items.add(item);
    }

    public void deleteItem(T item) {
        //items.remove(item);

		for(T it: items) {
			if(it.equals(item)) {
				items.remove(it);
				break;
			}
		}
    }

    public List<T> getItems() {
        return items;
    }

    public void deleteItems() {
        items.clear();
    }

    public boolean contains(T item){
        for(T it: items) {
            if(it.equals(item)) {
                return true;
            }
        }
        return false;
    }
}

