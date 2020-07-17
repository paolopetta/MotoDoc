package it.unisa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *	DTO dell'User Table
 *
 */

public class UserBean extends Bean implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String username;
    private String email;
    private String password;
    private String auth;
    private boolean active;
    private String CF;
    private String nome;
    private String cognome;
    private String indirizzo;

    public UserBean() {
        id = -1;
        username = email = password = auth = CF = nome = cognome = indirizzo = "";
        active = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCF() {
        return CF;
    }

    public void setCF(String CF){
        this.CF = CF;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getCognome(){
        return cognome  ;
    }

    public void setCognome(String cognome){
        this.cognome = cognome ;
    }

    public String getIndirizzo(){
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo){
        this.indirizzo = indirizzo;
    }

    public Boolean isAdmin(){
        if( auth == "admin") return true;
        else return false;
    }


    @Override
    public boolean equals(Object otherOb) {
        if(otherOb == null || otherOb.getClass() != getClass())
            return false;
        UserBean other = (UserBean) otherOb;
        return other.id == id;
    }

    @Override
    public UserBean clone() {
        UserBean bean = null;
        try {
            bean = (UserBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+ " [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
                + ", auth=" + auth + ", active=" + active + ", CF=" + CF + ", nome=" + nome+ ", cognome=" + cognome + ", indirizzo=" + indirizzo + "]";
    }

    /**
     * Ritorna (this.email)
     */
    @Override
    public List<String> getKey() {
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(email);
        return keys;
    }

    /**
     * Ritorna 0 se this.email = otherBean.email,
     * > 0 se this.email > otherBean.email,
     * < 0 se this.email < otherBean.email, rispetto all'ordine lessicografico
     */
    @Override
    public int compareKey(Bean otherBean) {
        if(this.getClass() != otherBean.getClass())
            return 1;
        UserBean other = (UserBean) otherBean;
        return email.compareTo(other.email);
    }

    @Override
    public String getBeanName() {
        return username;
    }
}
