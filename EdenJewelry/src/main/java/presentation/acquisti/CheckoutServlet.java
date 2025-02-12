package main.java.presentation.acquisti;

import main.java.dataManagement.bean.OrdineBean;
import main.java.dataManagement.bean.RigaOrdineBean;
import main.java.dataManagement.bean.UtenteBean;
import main.java.application.gestioneAcquisti.CheckoutControl;
import main.java.dataManagement.dao.OrdineDAO;
import main.java.application.gestioneAcquisti.Carrello;
import main.java.dataManagement.dao.ProdottoDAO;
import main.java.dataManagement.dao.RigaOrdineDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gaetano D'Alessio
 * Servlet che gestisce l'operazione di checkout.
 * Il metodo usato è descritto in
 * @see CheckoutControl
 */

public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource) getServletContext().getAttribute("MyDataSource");
        OrdineDAO dao = new OrdineDAO(ds);
        RigaOrdineDAO rigaDao = new RigaOrdineDAO(ds);

        String action = request.getParameter("RiepilogoOrdine");
        System.out.println("action nel riepilogo " + action);

        if (action.equals("view")) {

            HttpSession session = request.getSession();
            UtenteBean utente = (UtenteBean) session.getAttribute("utente"); //prendiamo l'utente dalla sessione
            String email = utente.getEmail(); //recuperiamo l'email
            System.out.println("email " + email);

            Carrello carrello = (Carrello) session.getAttribute("carrello"); //prendiamo il carrello dalla sessione

            String via = request.getParameter("via");
            String numero = request.getParameter("numero");
            String indirizzo = via + " " + numero;

            String carta = request.getParameter("carta");
            String scadenza = request.getParameter("scadenza");
            String cvv = request.getParameter("cvv");
            String metodoPagamento = carta + " " + scadenza + " " + cvv;

            if (CheckoutControl.checkout(carrello, utente.getEmail(), metodoPagamento, indirizzo, ds)) {
                request.setAttribute("order-success", "Ordine andato a buon fine");
            } else {
                request.setAttribute("order-failure", "Ordine fallito");
            }

            if (utente.getTipo().equals("user")) {
                List<OrdineBean> ordini = dao.doRetrieveByMail(email);
                request.setAttribute("ordini", ordini);
                List<RigaOrdineBean> righe = new ArrayList<RigaOrdineBean>();

                for (OrdineBean ordineBean : ordini) {
                    righe.addAll(rigaDao.doRetrieveByNumeroOrdine(ordineBean.getIdOrdine()));
                }
                request.setAttribute("righe", righe);
            } else {
                List<OrdineBean> ordini = dao.doRetrieveAll();
                request.setAttribute("ordini", ordini);
                List<RigaOrdineBean> righe = new ArrayList<>();

                for (OrdineBean ordineBean : ordini) {
                    righe.addAll(rigaDao.doRetrieveByNumeroOrdine(ordineBean.getIdOrdine()));
                }

                request.setAttribute("righe", righe);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/script/Ordini.jsp");
            dispatcher.forward(request, response);

        } else if (action.equals("review")) {
            HttpSession session = request.getSession();
            UtenteBean utente = (UtenteBean) session.getAttribute("utente"); //prendiamo l'utente dalla sessione
            String email = utente.getEmail(); //recuperiamo l'email

            if (utente.getTipo().equals("user")) {
                List<OrdineBean> ordini = dao.doRetrieveByMail(email);
                request.setAttribute("ordini", ordini);
                List<RigaOrdineBean> righe = new ArrayList<RigaOrdineBean>();

                for (OrdineBean ordineBean : ordini) {
                    righe.addAll(rigaDao.doRetrieveByNumeroOrdine(ordineBean.getIdOrdine()));
                }
                request.setAttribute("righe", righe);
            } else {
                List<OrdineBean> ordini = dao.doRetrieveAll();
                request.setAttribute("ordini", ordini);
                List<RigaOrdineBean> righe = new ArrayList<>();

                for (OrdineBean ordineBean : ordini) {
                    righe.addAll(rigaDao.doRetrieveByNumeroOrdine(ordineBean.getIdOrdine()));
                }

                request.setAttribute("righe", righe);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/script/Ordini.jsp");
                dispatcher.forward(request, response);

            }


        }
    }
}