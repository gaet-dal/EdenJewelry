package main.java.gestioneProdotti.homepage;

import main.java.gestioneProdotti.prodotto.ProdottoDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author Gaetano D'Alessio
 * Servlet per la ricerca del prodotto, gestista tramite il metodo search.
 * @see Strategy
 */

public class RicercaProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProdottoDAO prodotti;

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource) getServletContext().getAttribute("MyDataSource");
        prodotti = new ProdottoDAO(ds);

        String action = request.getParameter("submitAction");

        // if MOMENTANEO, da rivedere dopo aver risolto la jsp corrispondente
        /*
        if(action.equals("delete")){
            String query = request.getParameter("query");
            SimpleSearch simple = new SimpleSearch();
            simple.search("query", prodotti);
            request.setAttribute("resultQuery", simple.search(query, prodotti));

            RequestDispatcher rd = request.getRequestDispatcher(request.getContextPath()+"/RimozioneProdotto.jsp");
            rd.forward(request, response);
        }
         */

        String query = request.getParameter("query");
        String category = request.getParameter("category");
        Strategy strat;

        if(category != null && category.equals("yes"))
            strat = new CategorySearch();
        else
            strat = new SimpleSearch();

        request.setAttribute("resultQuery", strat.search(query, prodotti));

        RequestDispatcher rd = request.getRequestDispatcher(request.getContextPath()+"/ricercaProdotto.jsp");
        rd.forward(request, response);
    }
}
