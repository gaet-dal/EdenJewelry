package main.java.presentation.acquisti;

import main.java.application.gestioneAcquisti.Wishlist;
import main.java.dataManagement.bean.ItemWishlistBean;
import main.java.dataManagement.bean.UtenteBean;
import main.java.dataManagement.bean.WishlistBean;
import main.java.dataManagement.dao.ItemWishlistDAO;
import main.java.dataManagement.dao.WishlistDAO;

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
import java.util.List;
import java.util.logging.Logger;

//servlet che si occupa di stampare la wishlist;
public class WishlistServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    //usiamo questa servlet per gestire la richiesta di un utente di poter effettuare il login al sito e, in base ai dati inseriti,
    //restituaiamo una visione di questo consona al suo tipo (cioè seller o user);

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);

    }

    private static Logger logger = Logger.getLogger(WishlistServlet.class.getName());
    DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");
    private WishlistDAO wishlistDAO =new WishlistDAO(ds); //otteniamo il collegamento alla wishlist;
    private ItemWishlistDAO itemWishlistDAO = new ItemWishlistDAO(ds);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{

        HttpSession session=request.getSession();
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        Wishlist wish = new Wishlist();
        String email=request.getParameter("email");

        //questa variabile sta nella jsp della wishlist;
        String nome=request.getParameter("prodottoId"); //recuperiamo il nome del prodotto su cui è stato indicato che si vuole effettuare l'eliminazione
        int idItem = Integer.parseInt(request.getParameter("idItem"));

        String action = request.getParameter("WishlistAction"); // Recupera il valore del pulsante


        boolean ris=false;
        if(action.equals("rimuovi")){

            try {
                ris= wish.removeWishlist(idItem, ds);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if(ris){
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

        } else if (action.equals("aggiungi")) {
            ris = wish.aggiungiWishlist(nome, email, ds);

            if(!ris){
                request.setAttribute("wishlistadd-error", "aggiunta non andata a buon fine");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("wishlist.jsp");
                dispatcher.forward(request, response);
            }
        }

    }
}
