package it.unisa.model.dao;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import it.unisa.model.Bean;
import it.unisa.model.UserBean;

public interface DaoModel {

    public void doSave(Bean bean) throws SQLException;

    public void doSavePar(Bean bean) throws SQLException;

    public void doUpdate(Bean bean) throws SQLException;

    public boolean doDelete(List<String> keys) throws SQLException;

    public Bean doRetrieveByKey(List<String> keys) throws SQLException;

    public List<Bean> doRetrieveAll(Comparator<Bean> comparator) throws SQLException;

    public static UserBean doRetrieveByEmail(String email) throws SQLException {
        return null;
    }
}

