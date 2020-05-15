package it.unisa.model;

import java.io.Serializable;

public class ProductBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String codiceProd;	
	String nome;
    String descrizione;	
    double prezzo; 		
    String marca;		
    String disponibilità; 
    String p_iva;		
    String codiceAlfanumerico;	
    int codice;	
    
    
    public ProductBean() {
    	codiceProd= "-1";
    	nome="";
    	descrizione= "";
    	prezzo=0;
    	marca="";
    	disponibilità="";
    	p_iva="";
    	codiceAlfanumerico= "";
    	codice= -1;
    }

    
	public String getCodiceProd() {
		return codiceProd;
	}

	public void setCodiceProd(String codiceProd) {
		this.codiceProd = codiceProd;
	}
	
	public String getNome() {
    	return nome;
    }
    
    public void setNome(String nome) {
    	this.nome = nome;
    }

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getDisponibilità() {
		return disponibilità;
	}

	public void setDisponibilità(String disponibilità) {
		this.disponibilità = disponibilità;
	}

	public String getP_iva() {
		return p_iva;
	}

	public void setP_iva(String p_iva) {
		this.p_iva = p_iva;
	}

	public String getCodiceAlfanumerico() {
		return codiceAlfanumerico;
	}

	public void setCodiceAlfanumerico(String codiceAlfanumerico) {
		this.codiceAlfanumerico = codiceAlfanumerico;
	}

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}
	
	public boolean isEmpty() {
		return codiceProd.equals("-1");
	}

	@Override
	public String toString() {
		return "ProductBean [Nome Prodotto=" + nome + ", Codice Prodotto=" + codiceProd + ", Descrizione=" + descrizione + ", Prezzo=" + prezzo
				+ ", Marca=" + marca + ", Disponibilità=" + disponibilità + ", Partita iva=" + p_iva + ", Codice Alfanumerico="
				+ codiceAlfanumerico + ", Codice=" + codice + "]";
	}

	@Override
	public boolean equals(Object other) {
		return (this.getCodice() == ((ProductBean)other).getCodice());
	}
    
   
    
    
    
    
}
