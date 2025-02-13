package main.java.presentation.acquisti;

import main.java.application.gestioneAcquisti.Wishlist;
import main.java.dataManagement.bean.ItemWishlistBean;
import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.bean.UtenteBean;
import main.java.dataManagement.bean.WishlistBean;
import main.java.dataManagement.dao.ProdottoDAO;
import main.java.dataManagement.dao.WishlistDAO;
import main.java.dataManagement.dao.ItemWishlistDAO;

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

public class WishlistServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(WishlistServlet.class.getName());

    private WishlistDAO wishlistDAO;
    private ItemWishlistDAO itemWishlistDAO;
    private ProdottoDAO prodottoDAO;

    @Override
    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        System.out.println("WishlistServlet inizializzata.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Recupero della sessione
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utente") == null) {
            System.out.println("Sessione nulla o utente non trovato. Redirect a login.jsp.");
            response.sendRedirect(getServletContext().getContextPath()+"/script/login.jsp");
            return;
        }

        // Recupero dati utente
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        String email = utente.getEmail();

        // Recupero dell'azione (aggiungere o rimuovere)
        String action = request.getParameter("lista_desideri");
        //possiamo sfruttare questo pezzetto per permettere la vi
        if (action == null) {
            response.sendRedirect("wishlist.jsp");
            return;
        }

        // Creazione dell'oggetto Wishlist
        Wishlist wish = new Wishlist();
        boolean ris = false;

        // Recupero DataSource dal contesto della servlet
        DataSource ds = (DataSource) getServletContext().getAttribute("MyDataSource");
        if (ds == null) {
            System.out.println("Errore: DataSource è null.");
            throw new ServletException("DataSource non trovato");
        }

        wishlistDAO = new WishlistDAO(ds);
        itemWishlistDAO = new ItemWishlistDAO(ds);
        System.out.println("action della wishservlet: " + action);

        // Aggiunta alla wishlist
            if (action.equals("aggiungi")) {

                // Recupero del prodotto da aggiungere alla wishlist
                String nomeProdotto = request.getParameter("prodottoId");

                if(utente.getEmail()==null){
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/script/login.jsp");
                    dispatcher.forward(request, response);
                }

                if (nomeProdotto == null || nomeProdotto.trim().isEmpty()) {
                    response.sendRedirect("wishlist.jsp");
                    return;
                }

                WishlistBean Wishlist;
                try {
                     Wishlist=wishlistDAO.doRetrieveByEmail(email);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                int idWish=Wishlist.getIdWishlist();
               boolean c= itemWishlistDAO.existsByIdWishlistAndNomeProdotto(idWish, nomeProdotto);

                if(!c){
                    // Chiama il metodo per aggiungere il prodotto alla wishlist
                    ris = wish.aggiungiWishlist(nomeProdotto, email, ds);

                    if (!ris) {
                        System.out.println("Errore: aggiunta alla wishlist non riuscita.");
                        request.setAttribute("wishlistadd-error", "Aggiunta non andata a buon fine.");
                        RequestDispatcher dispatcher = request.getRequestDispatcher(getServletContext().getContextPath()+"/wishlist.jsp");
                        dispatcher.forward(request, response);
                    }

                    //questo lo settiamo per poter rimandare alla pagina dei dettagli prodotto dopo fatta l'aggiunta;
                    prodottoDAO=new ProdottoDAO(ds);
                    ProdottoBean prodotto;
                    try {
                        prodotto=prodottoDAO.doRetrieveByNome(nomeProdotto);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    request.setAttribute("prodotto", prodotto);
                    //settare una scritta che faccia capire che un prodotto è stato aggiunto alla wishlist;
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/script/DettagliProdotto.jsp");
                    dispatcher.forward(request, response);
                }
                else{

                    //questo lo settiamo per poter rimandare alla pagina dei dettagli prodotto dopo fatta l'aggiunta;
                    prodottoDAO=new ProdottoDAO(ds);
                    ProdottoBean prodotto;
                    try {
                        prodotto=prodottoDAO.doRetrieveByNome(nomeProdotto);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    request.setAttribute("prodotto", prodotto);
                    request.setAttribute("wish-error", "prodotto già presente");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/script/DettagliProdotto.jsp");
                    dispatcher.forward(request, response);
                }


            }

        // Rimozione dalla wishlist
        else if (action.equals("rimuovi")) {
            System.out.println("Tentativo di rimuovere il prodotto dalla wishlist...");

            // Recupero del prodotto da rimuovere alla wishlist
                String nomeProdotto = request.getParameter("prodottoId"); //reucperiamo l'id del prodotto che si vuole eliminare;

            //prendiamo l'id della wishlist tramite l'email con una ricerca;
            WishlistBean wishlist = new WishlistBean();

                try {
                    wishlist  =wishlistDAO.doRetrieveByEmail(email);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                int idWishlist=wishlist.getIdWishlist(); //recuperiamo l'id della wishlist;

                //avendo i nome del prodotto da eliminare e la wishlist collegata alla mail, passiamo all'eliminazione del prodotto;
                boolean eliminato=itemWishlistDAO.doDeleteByNomeProdottoEIdWishlist(nomeProdotto, idWishlist);

                System.out.println("Eliminato: " + eliminato);

                //questo lo settiamo per poter rimandare alla pagina dei dettagli prodotto dopo fatta l'aggiunta;
                if (eliminato) {
                    // Aggiorniamo la lista della wishlist
                    List<ItemWishlistBean> nuovaWishlist;

                    nuovaWishlist = itemWishlistDAO.doRetrieveByIdWishlist(idWishlist);

                    // Aggiorniamo la sessione con la nuova lista aggiornata
                    session.setAttribute("wishlist", nuovaWishlist);
                    // Dopo aver aggiornato la sessione, reindirizziamo alla wishlist JSP
                    response.sendRedirect(request.getContextPath() + "/script/wishlist.jsp");
                }
        }
        else if(action.equals("view")){
            System.out.println("email "+email);
            if(email!=null) {
                //da qui permettiamo la visualizzazione della wishlist quando si clicca sul button;
                try {
                    List<ItemWishlistBean> list = wish.viewWishList(email, ds);
                    System.out.println("wishlist " + list.toString());
                    session.setAttribute("wishlist", list);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/script/wishlist.jsp");
                    dispatcher.forward(request, response);
                } catch (SQLException e) {
                    System.out.println("non posso visualizzare la wishlist");
                    throw new RuntimeException(e);
                }
            }
            else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/script/login.jsp");
                dispatcher.forward(request, response);
            }
        }

        /*Redirect alla pagina della wishlist dopo l'operazione
        System.out.println("Operazione completata. Redirect a wishlist.jsp");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/script/wishlist.jsp");
        dispatcher.forward(request, response);

         */
    }
}
