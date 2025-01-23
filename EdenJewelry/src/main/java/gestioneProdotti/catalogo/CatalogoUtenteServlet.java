package main.java.gestioneProdotti.catalogo;

import main.java.gestioneProdotti.prodotto.ProdottoBean;
import main.java.gestioneProdotti.prodotto.ProdottoDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//si occupa solo di mostrare il catalogo dei prodotti del venditore;
public class CatalogoUtenteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    //usiamo questa servlet per gestire la richiesta di un utente di poter effettuare il login al sito e, in base ai dati inseriti,
    //restituaiamo una visione di questo consona al suo tipo (cioè seller o user);

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);

    }

    private ProdottoDAO prodottoDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");
        prodottoDAO=new ProdottoDAO(ds); //otteniamo collegamento alla tabella PRODOTTO;

        //poichè dobbiamo solo mostrare il carrello, recuperiamo tutti i prodotti e mandiamoli in stampa su una jsp;
        List <ProdottoBean> catalogo= new ArrayList<ProdottoBean>();

        try {
            catalogo=prodottoDAO.doRetrieveAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //mandiamo in stampa su una jsp;
        if(catalogo!=null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/.jsp"); //dobbiamo mandare sulla home;
            dispatcher.forward(request, response);
        }
    }
}
