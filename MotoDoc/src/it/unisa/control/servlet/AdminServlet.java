package it.unisa.control.servlet;

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

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    static ProductModelDM model = new ProductModelDM();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        if(action.equals("insert")) {
            String name = request.getParameter("name");
            String codProd = request.getParameter("codProd");
            String link = request.getParameter("img");
            String description = request.getParameter("description");
            int price = Integer.parseInt(request.getParameter("price"));
            String marca = request.getParameter("brand");
            String disponibilitá = request.getParameter("availability");
            String offerta = request.getParameter("offer");
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            ProductBean bean = new ProductBean();
            bean.setNome(name);
            bean.setCodiceProd(codProd);
            bean.setImg(link);
            bean.setDescrizione(description);
            bean.setPrezzo(price);
            bean.setMarca(marca);
            bean.setDisponibilità(disponibilitá);
            bean.setOfferta(offerta);
            bean.setQuantita(quantity);

            try {
                model.doSave(bean);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            request.setAttribute("message", "Prodotto "+ bean.getNome()+" Aggiunto");
        }
    }
}
