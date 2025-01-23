package main.java.gestioneProdotti.wishlist;

import main.java.gestioneAccount.utente.UtenteBean;
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

    private WishlistDAO wishlistDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
        DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");
        wishlistDAO=new WishlistDAO(ds); //otteniamo il collegamento alla wishlist;
        HttpSession session=request.getSession();
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");

        WishlistBean wishlist=null;
        try {
            wishlist = wishlistDAO.doRetrieveByEmail(utente.getEmail());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(wishlist!=null && utente.getEmail()!=null){
            //reindirizziamo alla jsp della wishlist;
            request.setAttribute("wishlist", wishlist); //settiamo la wishlist sulla request;
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/.jsp"); //dobbiamo mandare sulla home;
            dispatcher.forward(request, response);
        }


    }
}
