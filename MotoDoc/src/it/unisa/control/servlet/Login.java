package it.unisa.control.servlet;

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

@WebServlet("/login")
public class Login extends HttpServlet {

    private static final long serialVersionUID = 1L;
    //private final UserDao utenteDAO = new UserDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*HttpSession session = request.getSession();
        String action = request.getParameter("action");
        UserBean userBean= (UserBean) session.getAttribute("user");
        switch(action) {
            case "login":
                if(userBean == null) { // non c'é nessun utente loggato
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");

                    UserBean userRequested = UserDao.getUserByMail(email); //creo un nuovo userBean

                    //assegno al nuovo bean tutti i campi
                    if(userRequested != null) {
                        if(userRequested.getPassword().equals(password)) {
                            userBean = new UserBean();
                            userBean.setAuth(userRequested.getAuth());
                            userBean.setEmail(userRequested.getEmail());
                            userBean.setPassword(userRequested.getPassword());
                            userBean.setCF(userRequested.getCF());

                            //assegno l'user alla sessione
                            session.setAttribute("user", userBean);
                            // dopo che si é loggato lo rimando ad home
                            response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/home.jsp"));
                        }
                        else { //passw sbagliata
                            RequestDispatcher requestDispatcher= request.getServletContext().getRequestDispatcher("/login/login.jsp");
                            request.setAttribute("error", "password");
                            requestDispatcher.forward(request, response);
                        }
                    }
                    else { // utente non esiste
                        RequestDispatcher requestDispatcher= request.getServletContext().getRequestDispatcher("/login/login.jsp");
                        request.setAttribute("error", "notfound");
                    }
                }
                else response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/home.jsp"));
                break;
            case "logout":
                if(userBean != null) {
                    session.removeAttribute("user");
                }
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/home.jsp"));
                break;

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }*/
    }
}
