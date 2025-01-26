package main.java.gestioneProdotti.catalogo;

import main.java.gestioneAccount.utente.UtenteBean;
import main.java.gestioneProdotti.prodotto.ProdottoBean;
import main.java.gestioneProdotti.prodotto.ProdottoDAO;
import main.java.gestioneProdotti.catalogo.Catalogo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogoVenditoreServlet extends HttpServlet {

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

        HttpSession session=request.getSession();
        UtenteBean utente= (UtenteBean) session.getAttribute("utente"); //recuperare l'utente dalla sessione;

        //adesso recuperiamo il tipo dell'utente,
        //se è venditore, permettiamo di effettuare aggiunte e rimozioni dal catalogo;

        String tipo= utente.getTipo();
        if("seller".equals(tipo)){
            //se è venditore, recuperiamo il valore dell'azzione che vuole effettuare il venditore e reindirizziamo ai metodi specifici;

        }

        //poichè dobbiamo solo mostrare il carrello, recuperiamo tutti i prodotti e mandiamoli in stampa su una jsp;
        List<ProdottoBean> catalogo= new ArrayList<ProdottoBean>();

        try {
            catalogo=prodottoDAO.doRetrieveAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query = request.getParameter("query");
        if(query != null){
            Catalogo cat = new Catalogo(ds);
            ProdottoBean bean = new ProdottoBean();
            bean.setNome("query");
            try {
                cat.removeProduct(bean);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        //mandiamo in stampa su una jsp;
        if(catalogo!=null){
            request.setAttribute("catalogo", catalogo);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/.jsp"); //dobbiamo mandare sulla home;
            dispatcher.forward(request, response);
        }
    }
}
