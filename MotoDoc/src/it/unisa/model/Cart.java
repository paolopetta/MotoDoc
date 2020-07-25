package it.unisa.model;

import java.util.ArrayList;
import java.util.List;

public class Cart<T> {
    public static class ProdottoQuantita {
        private ProductBean prodotto;
        private int quantita;

        private ProdottoQuantita(ProductBean prodotto, int quantita) {
            this.prodotto = prodotto;
            this.quantita = quantita;
        }

        public int getQuantita() {
            return quantita;
        }

        public void setQuantita(int quantita) {
            this.quantita = quantita;
        }

        public ProductBean getProdotto() {
            return prodotto;
        }

        public double getPrezzo() {
            return (quantita * prodotto.getPrezzo());
        }
    }
    List<T> items;


    public Cart() {
        items = new ArrayList<T>();
    }

    public void addItem(T item) {
        items.add(item);
    }

    public void deleteItem(T item) {
        items.remove(item);

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

    public int getQuantita() {return quantita;}
}

