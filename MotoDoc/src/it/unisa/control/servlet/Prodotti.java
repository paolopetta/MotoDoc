package it.unisa.control.servlet;

import it.unisa.model.ProductModelDM;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/*
*********************************************************************************************************
*                                                                                                       *
*                                                                                                       *
*   dal div categorie passare tramite url la categoria per aprire la pagina prodotti con la cat giusta  *
*                                                                                                       *
*********************************************************************************************************
*/
@WebServlet("/prodotti")
public class Prodotti extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static ProductModelDM model = new ProductModelDM();

    public Prodotti(){super();}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sort = request.getParameter("sort");

        try {
            request.removeAttribute("products");
            request.setAttribute("products", model.doRetriveAll(sort));
        } catch(SQLException e) {
            System.out.println("Error: "+ e.getMessage());
            request.setAttribute("error", e.getMessage());
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/Prodotti.jsp");
        dispatcher.forward(request, response);

    }
}