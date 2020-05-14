package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.ProductBean;
import it.unisa.model.ProductModelDM;


@WebServlet("/ProductControl")
public class ProductControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static ProductModelDM model= new ProductModelDM();
	
    public ProductControl() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String sort = request.getParameter("sort");
		
		String action = request.getParameter("action");
		
		try {
			if(action != null) {
				if(action.equals("details")) {
					String id = request.getParameter("id");
					request.removeAttribute("Prodotto");
					request.setAttribute("Prodotto", model.doRetrieveByKey(id));
				}
				/*else if(action.equals("addCart")) {
					String id = request.getParameter("id");
					ProductBean bean = model.doRetrieveByKey(id);
					if(bean != null && !bean.isEmpty()) {
						cart.addItem(bean);
						request.setAttribute("message", "Product "+ bean.getName()+" added to cart");
					}
				} else if(action.equals("clearCart")) {
					cart.deleteItems();
					request.setAttribute("message", "Cart cleaned");
				} else if(action.equals("deleteCart")) {
					String id = request.getParameter("id");
					ProductBean bean = model.doRetrieveByKey(id);
					if(bean != null && !bean.isEmpty()) {
						cart.deleteItem(bean);
						request.setAttribute("message", "Product "+ bean.getName()+" deleted from cart");
					}
				} else if(action.equals("insert")) {
					String name = request.getParameter("name");
					String description = request.getParameter("description");
					int price = Integer.parseInt(request.getParameter("price"));
					int quantity = Integer.parseInt(request.getParameter("quantity"));
					
					ProductBean bean = new ProductBean();
					bean.setName(name);
					bean.setDescription(description);
					bean.setPrice(price);
					bean.setQuantity(quantity);
					
					model.doSave(bean);
					request.setAttribute("message", "Product "+ bean.getName()+" added");
				} else if(action.equals("delete")) {
					String id = request.getParameter("id");
					ProductBean bean = model.doRetrieveByKey(id);
					if(bean != null && !bean.isEmpty()) {
						model.doDelete(bean);
						request.setAttribute("message", "Product "+ bean.getName()+" deleted");
					}
				} else if(action.equals("update")) {
					String id = request.getParameter("id");
					String name = request.getParameter("name");
					String description = request.getParameter("description");
					int price = Integer.parseInt(request.getParameter("price"));
					int quantity = Integer.parseInt(request.getParameter("quantity"));	
					
					ProductBean bean = new ProductBean();
					bean.setCode(Integer.parseInt(id));
					bean.setName(name);
					bean.setDescription(description);
					bean.setPrice(price);
					bean.setQuantity(quantity);
					
					model.doUpdate(bean);
					request.setAttribute("message", "Product "+ bean.getName()+" updated");
				}*/
			}
		
		
		}catch (SQLException | NumberFormatException e) {
			System.out.println("Error: "+ e.getMessage());
			request.setAttribute("error", e.getMessage());
		}
		
		
		try {
			request.removeAttribute("products");
			request.setAttribute("products", model.doRetriveAll(sort));
		}catch (SQLException e) {
			System.out.println("Error: "+ e.getMessage());
			request.setAttribute("error", e.getMessage());
		}
		
		RequestDispatcher dispatcher= this.getServletContext().getRequestDispatcher("/ProductView.jsp");
		dispatcher.forward(request, response);
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
