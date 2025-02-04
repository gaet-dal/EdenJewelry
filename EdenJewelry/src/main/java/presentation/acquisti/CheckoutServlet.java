package main.java.presentation.acquisti;

import main.java.dataManagement.bean.UtenteBean;
import main.java.application.gestioneAcquisti.CheckoutControl;
import main.java.dataManagement.dao.OrdineDAO;
import main.java.application.gestioneAcquisti.Carrello;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author Gaetano D'Alessio
 * Servlet che gestisce l'operazione di checkout.
 * Il metodo usato è descritto in
 * @see CheckoutControl
 */

public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UtenteBean utente =(UtenteBean) session.getAttribute("utente");
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        String metodoPagamento = req.getParameter("metodoPagamento");
        String indirizzo = req.getParameter("indirizzo");

        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
        OrdineDAO ordineDAO = new OrdineDAO(ds);

        if(CheckoutControl.checkout(carrello, metodoPagamento, indirizzo, utente.getEmail(), ordineDAO)) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("OrderSuccess.jsp");
            dispatcher.forward(req, resp);
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("OrderError.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
