package main.java.presentation.home;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.io.IOException;

public class HomeServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Se hai dati da caricare, puoi recuperarli qui e aggiungerli alla request
        // Esempio: request.setAttribute("prodotti", prodottoDAO.doRetrieveAll());

        // Inoltra la richiesta alla homepage.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath()+"homepage.jsp");
        dispatcher.forward(request, response);
    }
}
