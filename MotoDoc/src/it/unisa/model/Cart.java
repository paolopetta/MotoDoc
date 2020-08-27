package it.unisa.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    ArrayList<ProductBean> items;

    public Cart() {
        items = new ArrayList<ProductBean>();
    }

    public void addItem(ProductBean item) {
        items.add(item);
    }

    public boolean alReadyIn(ProductBean item) {
        for(ProductBean it: items) {
            if(it.getCodiceProd().equals(item.codiceProd)) {
                return true;
            }
        }
        return false;
    }

    public void deleteItem(ProductBean item) {
        //items.remove(item);

        for(ProductBean it: items) {
            if(it.equals(item)) {
                items.remove(it);
                break;
            }
        }
    }

    public ArrayList<ProductBean> getItems() {
        return items;
    }

    public void deleteItems() {
        items.clear();
    }

    public boolean contains(ProductBean item){
        for(ProductBean it: items) {
            if(it.equals(item)) {
                return true;
            }
        }
        return false;
    }
}

