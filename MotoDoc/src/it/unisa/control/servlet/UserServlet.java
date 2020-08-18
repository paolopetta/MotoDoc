package it.unisa.control.servlet;

import it.unisa.model.dao.OrdineDao;
import it.unisa.model.dao.ProductModelDM;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static OrdineDao model = new OrdineDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.removeAttribute("ordini");
            request.setAttribute("ordini", model.doRetriveAll());
        } catch(SQLException e) {
            System.out.println("Error: "+ e.getMessage());
            request.setAttribute("error", e.getMessage());
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/User/User.jsp");
        requestDispatcher.forward(request, response);
    }
}
