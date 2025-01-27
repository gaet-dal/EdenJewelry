package main.java.gestioneProdotti.wishlist;

import main.java.gestioneAccount.utente.UtenteBean;
import main.java.gestioneProdotti.prodotto.ProdottoBean;
import main.java.gestioneProdotti.prodotto.ProdottoDAO;

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

//servlet che si occupa di stampare la wishlist;
public class WishlistServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    //usiamo questa servlet per gestire la richiesta di un utente di poter effettuare il login al sito e, in base ai dati inseriti,
    //restituaiamo una visione di questo consona al suo tipo (cioè seller o user);

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);

    }


    DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");
    private WishlistDAO wishlistDAO =new WishlistDAO(ds); //otteniamo il collegamento alla wishlist;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{

        HttpSession session=request.getSession();
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        String email=request.getParameter("email");

        //questa variabile sta nella jsp della wishlist;
        String nome=request.getParameter("prodottoId"); //recuperiamo il nome del prodotto su cui è stato indicato che si vuole effettuare l'eliminazione

        String action = request.getParameter("WishlistAction"); // Recupera il valore del pulsante
        /*
        WishlistBean wishlist=null;
        try {
            wishlist = wishlistDAO.doRetrieveByEmail(utente.getEmail());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(wishlist!=null && utente.getEmail()!=null){
            //reindirizziamo alla jsp della wishlist;
            request.setAttribute("wishlist", wishlist); //settiamo la wishlist sulla request;
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("wishlist.jsp"); //dobbiamo mandare il risultato sulla wishlist; ;
            dispatcher.forward(request, response);
        }
        */

        boolean ris=false;
        if(action.equals("rimuovi")){

            try {
                ris= removeWishlist(nome, email);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if(ris==true){
                //se l'operazione è andata a buonfine, allora reidirizziamo sulla jsp;
                //il recuperò della wishlist verrà effettuato direttamente da quest'ultima;
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("wishlist.jsp"); //dobbiamo mandare sulla home;
                dispatcher.forward(request, response);
            }
            else {
                request.setAttribute("wishlistremove-error", "rimozione non andata a buonfine");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("wishlist.jsp"); //dobbiamo mandare sulla home;
                dispatcher.forward(request, response);
            }

        }

    }

    //inserire nel file i metodi di aggiunta e rimozione dalla wishlist;

    public boolean AggiungiWishlist(String nome, String email) {
        //usiamo il nome del prodotto (chiave primaria) per recuoerare tuttu i suoi dati dal db;
        ProdottoDAO prodottoDAO=new ProdottoDAO(ds);
        ProdottoBean prodotto=null;
        try {
            prodotto = prodottoDAO.doRetrieveByNome(nome);
        } catch (SQLException e) {
            //il prodotto non esiste
            return false;
        }

        WishlistDAO wishlistDAO = new WishlistDAO(ds);
        boolean aggiungi=false;
        try {
            WishlistBean wishlist = wishlistDAO.doRetrieveByEmail(email);
            wishlist.addProdotto(prodotto);
           aggiungi= wishlistDAO.doSave(wishlist);
        } catch (SQLException e) {
            return false;
        }

        return aggiungi; //restituiiamo l'esito dell'operazione doSave;
    }

    public boolean removeWishlist(String nome, String email) throws SQLException {
        WishlistDAO wishlistDAO = new WishlistDAO(ds);
        boolean rimuovi=false;

        //recuperiamo la lista salvata nel db e manteniamola in un oggetto WishlistBean;

        WishlistBean wishlist = wishlistDAO.doRetrieveByEmail(email);


        wishlist.removeProdotto(nome); //eliminiamo il prodotto dalla lista che abbiamo salvato localmente;

        //risalvimao la lista nel db;
        //la lista va a sovrascrivere quella già presente nel db;
        boolean aggiungi= wishlistDAO.doSave(wishlist);


        return aggiungi;
    }

}
