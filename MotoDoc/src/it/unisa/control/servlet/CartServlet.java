package it.unisa.control.servlet;

import it.unisa.model.Cart;
import it.unisa.model.ProductBean;
import it.unisa.model.ProductModelDM;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    ProductModelDM model = new ProductModelDM();

    public CartServlet(){super(); }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        @SuppressWarnings("unchecked")
        Cart<ProductBean> cart = (Cart<ProductBean>) request.getSession().getAttribute("carrello");

        //ancora non ho nessun carrello abbinato alla sessione
        if (cart == null) {
            cart = new Cart<ProductBean>();
            request.getSession().setAttribute("carrello", cart);
        }

        String action = request.getParameter("action");

        try { //crea switch
            if (action != null) {
                if (action.equals("addCart")) {
                    String id = request.getParameter("id");
                    ProductBean bean = null;
                    bean = model.doRetrieveByKey(id);

                    if (bean != null && !bean.isEmpty()) {
                        cart.addItem(bean);
                        request.setAttribute("message", "Product " + bean.getNome() + " added to cart");
                    }
                }else if(action.equals("clearCart")) {
                    cart.deleteItems();
                    request.setAttribute("message", "Cart cleaned");
                } else if(action.equals("deleteCart")) {
                    String id = request.getParameter("id");
                    ProductBean bean = model.doRetrieveByKey(id);
                    if(bean != null && !bean.isEmpty()) {
                        cart.deleteItem(bean);
                        request.setAttribute("message", "Product "+ bean.getNome()+" deleted from cart");
                    }
                }
            }
        }catch(SQLException e) {
            System.out.println("Error: "+ e.getMessage());
            request.setAttribute("error", e.getMessage());
        }
        request.setAttribute("cart", cart);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/Cart.jsp");
        dispatcher.forward(request, response);
    }
}






