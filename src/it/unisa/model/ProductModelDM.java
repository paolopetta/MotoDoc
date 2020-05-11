package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class ProductModelDM implements ProductModel<ProductBean> {

	@Override
	public ProductBean doRetrieveByKey(String codiceProd) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ProductBean bean= new ProductBean();
		
		String selectSQL="SELECT * FROM product WHERE code= ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, Integer.parseInt(codiceProd));
			
			System.out.println("doRetrieveByKey:" + preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				
				bean.setCodiceProd(rs.getString("codiceProd"));
				bean.setNome(rs.getString("nome"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getDouble("prezzo"));
				bean.setMarca(rs.getString("marca"));
				bean.setDisponibilit�(rs.getString("disponibilit�"));
				bean.setP_iva(rs.getString("p_iva"));
				bean.setCodiceAlfanumerico(rs.getString("codiceAlfanumerico"));
				bean.setCodice(rs.getInt("codice"));
				
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
	public Collection<ProductBean> doRetriveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<ProductBean> products = new LinkedList<ProductBean>();
		
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
				bean.setPrezzo(rs.getDouble("prezzo"));
				bean.setMarca(rs.getString("marca"));
				bean.setDisponibilit�(rs.getString("disponibilit�"));
				bean.setP_iva(rs.getString("p_iva"));
				bean.setCodiceAlfanumerico(rs.getString("codiceAlfanumerico"));
				bean.setCodice(rs.getInt("codice"));
				
				products.add(bean);
			}
		} finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return products;
		
		
	}

	@Override
	public void doSave(ProductBean product) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doUpdate(ProductBean product) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(ProductBean product) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
