package it.unisa.model;

import java.sql.SQLException;
import java.util.Collection;

//per ogni tab
public interface ProductModel<T> {

    public T doRetrieveByKey(String codiceProd) throws SQLException;

    public Collection<T> doRetriveAll(String order) throws SQLException;

    public void doSave(T product) throws SQLException;

    public void doUpdate(T product) throws SQLException;

    public void doDelete(T product) throws SQLException;
    //volendo possiamo passarci il codice
    //public void doDeleteCode(int codice)

}