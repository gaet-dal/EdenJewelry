package main.java.presentation.home;

import main.java.application.gestioneCatalogo.Ricerca;
import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.dao.ProdottoDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gaetano D'Alessio
 * Servlet per la ricerca del prodotto, gestista tramite il metodo search.
 */

public class RicercaProdottoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String submitAction=request.getParameter("submitAction");
        List<ProdottoBean> listaProdotti = new ArrayList<>();

        try {
            DataSource ds = (DataSource) getServletContext().getAttribute("MyDataSource");
            ProdottoDAO prodottoDAO = new ProdottoDAO(ds);

            if (query != null) {
                listaProdotti = prodottoDAO.cercaProdottiPerNome(query); // Metodo che esegue la query
                System.out.println(listaProdotti.toString());
            }

            System.out.println("DEBUG: Numero prodotti trovati = " + listaProdotti.size()); // Controllo

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Imposta il risultato della ricerca nella request
        request.setAttribute("resultQuery", listaProdotti);

        // Inoltra la richiesta alla JSP
        if(submitAction.equals("delete")){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/script/RimozioneProdotto.jsp");
            dispatcher.forward(request, response);
        }
        else if(submitAction.equals("view")){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/script/Ricerca.jsp");
            dispatcher.forward(request, response);
        }

    }
}