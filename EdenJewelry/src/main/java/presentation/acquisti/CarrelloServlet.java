package main.java.presentation.acquisti;

import main.java.application.gestioneAcquisti.Carrello;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//servlet che gestisce la visione del carrello
public class CarrelloServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    //usiamo questa servlet per gestire la richiesta di un utente di poter effettuare il login al sito e, in base ai dati inseriti,
    //restituaiamo una visione di questo consona al suo tipo (cioè seller o user);

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        //recuperiamo il carrello dalla sessione
        HttpSession session=request.getSession(false); //la session non deve essere ricreata se non esiste;
        Carrello carrello = (Carrello) session.getAttribute("carrello"); //recuperiamo il carrello dalla sessione;

        //chiamiamo il metodo showcarrello per la stampa, se non è vuoto;
        if(carrello!=null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/carrello.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Devi effettuare l'accesso");
        }
    }
}
