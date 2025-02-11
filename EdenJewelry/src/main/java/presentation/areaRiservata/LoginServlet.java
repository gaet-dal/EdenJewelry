package main.java.presentation.areaRiservata;

import main.java.application.gestioneAccount.GestioneAutenticazione;
import main.java.dataManagement.bean.ItemWishlistBean;
import main.java.dataManagement.bean.UtenteBean;
import main.java.dataManagement.bean.WishlistBean;
import main.java.dataManagement.dao.ItemWishlistDAO;
import main.java.dataManagement.dao.UtenteDAO;

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

import main.java.application.gestioneAcquisti.Carrello;
import main.java.dataManagement.dao.WishlistDAO;
import main.java.utilities.HashingAlgoritm;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //usiamo questa servlet per gestire la richiesta di un utente di poter effettuare il login al sito e, in base ai dati inseriti,
    //restituaiamo una visione di questo consona al suo tipo (cioè seller o user);

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);

    }
    private UtenteDAO utenti; //creiamo l'oggetto in grado di interagire che il contenuto del database;
    private WishlistDAO wishlistDAO;
    private ItemWishlistDAO itemWishlistDAO;

    //questo perchè, una volta che ci vengono fortniti email e password, è necessario confrontare questi parametri con le varie entry del database alla ricerca di una corrispondenza;
    //se non vi è riscontro, bisogna informae l'utente che sta provando ad affettuare il login, che deve  reinserire le credenziali;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        //stabiliamo una connessione con il db, per mezzo del dao instanziato;
        DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");
        utenti=new UtenteDAO(ds);
        wishlistDAO = new WishlistDAO(ds);
        itemWishlistDAO = new ItemWishlistDAO(ds);

        GestioneAutenticazione gest = new GestioneAutenticazione();
        HashingAlgoritm hash=new HashingAlgoritm(); //creiamo un oggetto per poter usare il metodo per effettuare l'hashing della password;
        //acquisiamo in ingresso i parametri dell'utente e valutiamo le se credenziali sono corrette;
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String action = request.getParameter("action");//recuperiamo il parametro action daalla request;
        //se corrisponde al logout, allora eseguiamo il metodo;

        // Controlla se l'azione richiesta è "logout"
        System.out.println("action "+ action);
        if ("logout".equals(action)) {
           HttpSession session = request.getSession(false);
           boolean ris= gest.logout(session);
           System.out.println("ris "+ris);

            response.sendRedirect(request.getContextPath() + "/HomeServlet");
            return; // ⬅ Questo impedisce l'esecuzione del resto del codice
        }

        //
        String hashing=hash.toHash(password); //effettuaiamo l'hasing della password;

        //chiamiamo il metodo login per valitare se c'è corrispondenza tra le credenziali date in put e quelle nel db;
        UtenteBean b;

        try {
            b=gest.login(email, hashing, utenti);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(b!=null) {
            //se l'utente non è null, allora c'è corrispondeza e creiamo la sessione per l'utente;
            HttpSession session = request.getSession();
            session.setAttribute("utente", b);
            //una volta verificato l'utente, bisogna reindirizzarlo verso la sua area utente;

            //creiamo anche il carrello;
            Carrello cart = new Carrello(email);//passiamo la mail, in modo che venga associata a un utente in particolare;
            session.setAttribute("carrello", cart);

            WishlistBean wishlistBean = new WishlistBean();

            System.out.println(email);

            try {
                wishlistBean = wishlistDAO.doRetrieveByEmail(email);
            } catch (SQLException e) {
                System.out.println("Errore durante il recupero della wishlist");
            }


            List<ItemWishlistBean> list = itemWishlistDAO.doRetrieveByIdWishlist(wishlistBean.getIdWishlist());
            if(list == null){
                System.out.println("La lista è vuota");
            }
            session.setAttribute("wishlist" , list);

            //prendiamo il tipo per effettuare la ridirezione al profilo specifico;
            String tipo = b.getTipo();
            if (tipo.equals("user")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/script/profiloUtente.jsp");
                dispatcher.forward(request, response);
            }
            else if(tipo.equals("seller")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/script/ProfiloVenditore.jsp");
                dispatcher.forward(request, response);
            }
        }
        else{
            request.setAttribute("login-error", "Credenziali non valide");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/script/login.jsp");
            dispatcher.forward(request, response);
            //bisogn richiedere all'utente di reiserire le credenziali;
        }

    }
}
