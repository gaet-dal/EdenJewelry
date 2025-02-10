package main.java.presentation.home;

import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.dao.ProdottoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Se hai dati da caricare, puoi recuperarli qui e aggiungerli alla request
        // Esempio: request.setAttribute("prodotti", prodottoDAO.doRetrieveAll());

        DataSource ds = (DataSource) request.getServletContext().getAttribute("MyDataSource");
        ProdottoDAO prodottoDAO = new ProdottoDAO(ds);
        List<ProdottoBean> list;



        try {
            System.out.print("prima del retrieve");
            list = prodottoDAO.doRetrieveAll();
            System.out.println("Numero di prodotti trovati in HomeServlet: " + list.size());
            System.out.print("prima del retrieve");

            for(ProdottoBean prodottoBean : list) {
                System.out.println("prodotto "+ prodottoBean.toString());
            }

        } catch (SQLException e) {
            //problema sulla ridirezione sulla home quando il catalogo è vuoto;
            System.out.println(e);
            throw new RuntimeException(e);
        }
        request.setAttribute("prodotti", list);

        // Inoltra la richiesta alla homepage.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/");
        dispatcher.forward(request, response);
    }
}
