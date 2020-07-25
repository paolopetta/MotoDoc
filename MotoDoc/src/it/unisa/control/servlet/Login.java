package it.unisa.control.servlet;

import it.unisa.model.Cart;
import it.unisa.model.ProductBean;
import it.unisa.model.UserBean;
import it.unisa.model.dao.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet("/login")
public class Login extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UserDao utenteDAO = new UserDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        UserBean userBean= (UserBean) session.getAttribute("user");
        Cart<ProductBean> cart= (Cart<ProductBean>) session.getAttribute("carrello");

        if(action.equals("login")){
            if(userBean == null) { // non c'é nessun utente loggato
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                UserBean userRequested = null; //creo un nuovo userBean
                try {
                    userRequested = UserDao.doRetrieveByEmail(email);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                //assegno al nuovo bean tutti i campi
                if(userRequested != null) {
                    MessageDigest digest = null;
                    try {
                        digest = MessageDigest.getInstance("SHA-1");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    digest.reset();
                        digest.update(password.getBytes(StandardCharsets.UTF_8));
                        String passHash = String.format("%040x", new BigInteger(1, digest.digest()));

                    if(userRequested.getPassword().equals(passHash)) {
                        userBean = new UserBean();
                        userBean.setAuth(userRequested.getAuth());
                        userBean.setEmail(userRequested.getEmail());
                        userBean.setPassword(userRequested.getPassword());
                        userBean.setNome(userRequested.getNome());
                        userBean.setCognome(userRequested.getCognome());
                        userBean.setCF(userRequested.getCF());

                        //assegno l'user alla sessione
                        session.setAttribute("user", userBean);
                        session.setAttribute("carrello", cart); //prima cart cart
                        // dopo che si é loggato lo rimando ad home
                        //response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/home.jsp"));
                    }
                    else { //passw sbagliata
                        RequestDispatcher requestDispatcher= request.getServletContext().getRequestDispatcher("/login.jsp");
                        request.setAttribute("error", "password");
                        requestDispatcher.forward(request, response);
                    }
                }
                else { // utente non esiste
                    RequestDispatcher requestDispatcher= request.getServletContext().getRequestDispatcher("/login.jsp");
                    request.setAttribute("error", "notfound");
                }
            }
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/home.jsp"));

        }

        else if(action.equals("logout")){
            System.out.println("Dentro logout");
            if (userBean != null) {
                session.removeAttribute("user");
                cart.deleteItems();
                //session.removeAttribute("cart");
            }
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/home.jsp"));
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        UserBean userBean= (UserBean) session.getAttribute("user");
        //Cart<ProductBean> cart= (Cart<ProductBean>) session.getAttribute("cart");

        if(action.equals("logout")){

            if (userBean != null) {
                session.removeAttribute("user");
                //cart.deleteItems();
                //session.removeAttribute("cart");

            }
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/home.jsp"));
        }
    }

}
