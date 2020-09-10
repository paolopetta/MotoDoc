package it.unisa.control.servlet;

import it.unisa.model.Cart;
import it.unisa.model.ProductBean;
import it.unisa.model.dao.ProductModelDM;

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

    static ProductModelDM model = new ProductModelDM();


    public CartServlet() {
        super();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        @SuppressWarnings("unchecked")
        HttpSession session = request.getSession();
        Cart cart = (Cart)request.getSession().getAttribute("carrello");
        if(cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("carrello", cart);
        }

        String sort = request.getParameter("sort");

        String action = request.getParameter("action");

        try {
            if(action != null) {
                if(action.equals("details")) {
                    String id = request.getParameter("id");
                    request.removeAttribute("product");
                    request.setAttribute("product", model.doRetrieveByKey(id));
                } else if(action.equals("addCart")) {
                    String id = request.getParameter("id");
                    ProductBean bean = model.doRetrieveByKey(id);
                    if(bean != null && !bean.isEmpty()) {
                        if(cart.alReadyIn(bean)) {
                            cart.incrementItem(bean);
                        }
                        else
                            cart.addItem(bean);
                        request.setAttribute("message", "Product "+ bean.getNome()+" added to cart");
                    }
                } else if(action.equals("clearCart")) {
                    cart.deleteItems();
                    request.setAttribute("message", "Cart cleaned");
                } else if(action.equals("deleteCart")) {
                    String id = request.getParameter("id");
                    ProductBean bean = model.doRetrieveByKey(id);
                    if(bean != null && !bean.isEmpty()) {
                        cart.deleteItem(bean);
                        request.setAttribute("message", "Product "+ bean.getNome()+" deleted from cart");
                    }

                } else if(action.equals("insert")) {
                    String nome = request.getParameter("nome");
                    String descrizione = request.getParameter("descrizione");
                    int prezzo = Integer.parseInt(request.getParameter("prezzo"));

                    ProductBean bean = new ProductBean();
                    bean.setNome(nome);
                    bean.setDescrizione(descrizione);
                    bean.setPrezzo(prezzo);

                    model.doSave(bean);
                    request.setAttribute("message", "Product "+ bean.getNome()+" added");
                } else if(action.equals("delete")) {
                    String id = request.getParameter("id");
                    ProductBean bean = model.doRetrieveByKey(id);
                    if(bean != null && !bean.isEmpty()) {
                        model.doDelete(bean, "prodotto");
                        request.setAttribute("message", "Product "+ bean.getNome()+" deleted");
                    }
                } else if(action.equals("update")) {
                    String id = request.getParameter("id");
                    String nome = request.getParameter("nome");
                    String descrizione= request.getParameter("descrizione");
                    int prezzo = Integer.parseInt(request.getParameter("prezzo"));

                    ProductBean bean = new ProductBean();
                    bean.setCodiceProd(id);
                    bean.setNome(nome);
                    bean.setDescrizione(descrizione);
                    bean.setPrezzo(prezzo);

                    model.doUpdate(bean);
                    request.setAttribute("message", "Product "+ bean.getNome()+" updated");
                }
            }
        } catch(NumberFormatException | SQLException e) {
            System.out.println("Error: "+ e.getMessage());
            request.setAttribute("error", e.getMessage());
        }

        session.setAttribute("cart", cart);


        /*RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Cart.jsp");
        requestDispatcher.forward(request, response);*/
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/Cart.jsp"));

    }

}






