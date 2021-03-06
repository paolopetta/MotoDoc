package it.unisa.model.dao;

import it.unisa.model.DriverManagerConnectionPool;
import it.unisa.model.ProductBean;
import it.unisa.model.dao.ProductModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class ProductModelDM implements ProductModel<ProductBean> {

    @Override
    public ProductBean doRetrieveByKey(String codiceProd) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ProductBean bean= new ProductBean();

        String selectSQL="SELECT * FROM Prodotto WHERE codiceProd= ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, codiceProd);

            System.out.println("doRetrieveByKey:" + preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {

                bean.setCodiceProd(rs.getString("codiceProd"));
                bean.setNome(rs.getString("nome"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setMarca(rs.getString("marca"));
                bean.setDisponibilità(rs.getString("disponibilità"));
                bean.setQuantita(1);

            }
        } finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }

        return bean;

    }

    @Override
    public Collection<ProductBean> doRetriveAll(String order){
       Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProductBean> products = new ArrayList<ProductBean>();

        String selectSQL = "SELECT * FROM Prodotto";

        if(order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            System.out.println("doRetrieveAll:" + preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                ProductBean bean = new ProductBean();

                bean.setCodiceProd(rs.getString("codiceProd"));
                bean.setNome(rs.getString("nome"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setImg(rs.getString("img"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setMarca(rs.getString("marca"));
                bean.setDisponibilità(rs.getString("disponibilità"));
                bean.setOfferta(rs.getString("offerta"));


                products.add(bean);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
                DriverManagerConnectionPool.releaseConnection(connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return products;
    }


    @Override
    public Collection<ProductBean> doRetriveOfferte(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProductBean> offerte = new LinkedList<ProductBean>();


        String selectSQL = "select *\n" +
                "from Prodotto as P\n" +
                "where P.offerta ='y'";

        if(order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            System.out.println("doRetrieveOfferte:" + preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                ProductBean bean = new ProductBean();

                bean.setCodiceProd(rs.getString("codiceProd"));
                bean.setNome(rs.getString("nome"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setImg(rs.getString("img"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setMarca(rs.getString("marca"));
                bean.setDisponibilità(rs.getString("disponibilità"));
                bean.setOfferta(rs.getString("offerta"));


                offerte.add(bean);
            }
        } finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return offerte;
    }

    @Override
    public Collection<ProductBean> doRetrivePneumatici(String order){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProductBean> pneumatici = new ArrayList<ProductBean>();

        String selectSQL = "SELECT *\n" +
                "FROM Pneumatici as PN, Prodotto as P\n" +
                "WHERE P.codiceProd = PN.codiceProd";

        if(order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            System.out.println("doRetrievePneumatici:" + preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                ProductBean bean = new ProductBean();

                bean.setCodiceProd(rs.getString("codiceProd"));
                bean.setNome(rs.getString("nome"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setImg(rs.getString("img"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setMarca(rs.getString("marca"));
                bean.setDisponibilità(rs.getString("disponibilità"));
                bean.setOfferta(rs.getString("offerta"));

                pneumatici.add(bean);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
                DriverManagerConnectionPool.releaseConnection(connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return pneumatici;
    }

    @Override
    public Collection<ProductBean> doRetriveCarrozzeria(String order){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProductBean> carrozzerie = new ArrayList<ProductBean>();


        String selectSQL = "select *\n" +
                "from Carrozzeria as C, Prodotto as P\n" +
                "where P.codiceProd = C.codiceProd";

        if(order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            System.out.println("doRetrieveCarrozzeria:" + preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                ProductBean bean = new ProductBean();

                bean.setCodiceProd(rs.getString("codiceProd"));
                bean.setNome(rs.getString("nome"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setImg(rs.getString("img"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setMarca(rs.getString("marca"));
                bean.setDisponibilità(rs.getString("disponibilità"));
                bean.setOfferta(rs.getString("offerta"));
                bean.setMateriale(rs.getString("materiale"));

                carrozzerie.add(bean);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
            finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
                DriverManagerConnectionPool.releaseConnection(connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return carrozzerie;
    }

    @Override
    public Collection<ProductBean> doRetriveMeccanica(String order) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProductBean> meccanica = new ArrayList<ProductBean>();

        String selectSQL = "SELECT *\n" +
                "FROM meccanica as M, Prodotto as P\n" +
                "WHERE P.codiceProd = M.codiceProd";

        if(order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            System.out.println("doRetrieveMeccanica:" + preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                ProductBean bean = new ProductBean();

                bean.setCodiceProd(rs.getString("codiceProd"));
                bean.setNome(rs.getString("nome"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setImg(rs.getString("img"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setMarca(rs.getString("marca"));
                bean.setDisponibilità(rs.getString("disponibilità"));
                bean.setOfferta(rs.getString("offerta"));
                bean.setImpiego(rs.getString("impiego"));

                meccanica.add(bean);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
                DriverManagerConnectionPool.releaseConnection(connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return meccanica;
    }


    @Override
    public void doSave(ProductBean product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO Prodotto" +
                "(codiceProd, nome, descrizione, img, prezzo, marca, disponibilità,offerta) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, product.getCodiceProd());
            preparedStatement.setString(2, product.getNome());
            preparedStatement.setString(3, product.getDescrizione());
            preparedStatement.setString(4, product.getImg());
            preparedStatement.setDouble(5, product.getPrezzo());
            preparedStatement.setString(6, product.getMarca());
            preparedStatement.setString(7, product.getDisponibilità());
            preparedStatement.setString(8, product.getOfferta());


            System.out.println("doSave: "+ preparedStatement.toString());
            preparedStatement.executeUpdate();

            connection.commit();

        } finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }

    public void doSaveCarr(ProductBean product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO carrozzeria" +
                "(codiceProd, materiale) VALUES (?, ?);";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, product.getCodiceProd());
            preparedStatement.setString(2, product.getMateriale());

            System.out.println("doSaveCarr: "+ preparedStatement.toString());
            preparedStatement.executeUpdate();

            connection.commit();

        } finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }

    public void doSavePneu(ProductBean product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO pneumatici" +
                "(codiceProd, misura , stagione) VALUES (?, ?, ?);";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, product.getCodiceProd());
            preparedStatement.setString(2, product.getMisure());
            preparedStatement.setString(2, product.getStagione());

            System.out.println("doSavePneu: "+ preparedStatement.toString());
            preparedStatement.executeUpdate();

            connection.commit();

        } finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }

    public void doSaveMecc(ProductBean product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO meccanica" +
                "(codiceProd, impiego ) VALUES (?, ?);";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, product.getCodiceProd());
            preparedStatement.setString(2, product.getImpiego());


            System.out.println("doSaveMecc: "+ preparedStatement.toString());
            preparedStatement.executeUpdate();

            connection.commit();

        } finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }




    @Override
    public void doUpdate(ProductBean product) throws SQLException {


    }

    @Override
    public void doDelete(ProductBean product, String table) throws SQLException {
        String codiceProd = product.getCodiceProd();
        try (Connection con = DriverManagerConnectionPool.getConnection()) {
            String sql = "DELETE FROM "+ table+ " WHERE codiceProd=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, codiceProd);
            ps.executeUpdate();
            con.commit();
             ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
