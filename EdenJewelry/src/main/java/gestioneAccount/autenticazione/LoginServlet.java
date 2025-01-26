package main.java.gestioneAccount.autenticazione;

import com.sun.deploy.net.HttpRequest;
import main.java.gestioneAccount.utente.UtenteBean;
import main.java.gestioneAccount.utente.UtenteDAO;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

import main.java.gestioneProdotti.carrello.Carrello;
import main.java.utilities.HashingAlgoritm;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //usiamo questa servlet per gestire la richiesta di un utente di poter effettuare il login al sito e, in base ai dati inseriti,
    //restituaiamo una visione di questo consona al suo tipo (cioè seller o user);

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);

    }
private UtenteDAO utenti; //creiamo l'oggetto in grado di interagire che il contenuto del database;

    //questo perchè, una volta che ci vengono fortniti email e password, è necessario confrontare questi parametri con le varie entry del database alla ricerca di una corrispondenza;
    //se non vi è riscontro, bisogna informae l'utente che sta provando ad affettuare il login, che deve  reinserire le credenziali;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        //stabiliamo una connessione con il db, per mezzo del dao instanziato;
        DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");
        utenti=new UtenteDAO(ds);
        HashingAlgoritm hash=new HashingAlgoritm(); //creiamo un oggetto per poter usare il metodo per effettuare l'hashing della password;
        //acquisiamo in ingresso i parametri dell'utente e valutiamo le se credenziali sono corrette;
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String action = request.getParameter("action");//recuperiamo il parametro action daalla request;
        //se corrisponde al logout, allora eseguiamo il metodo;

        // Controlla se l'azione richiesta è "logout"
        if ("logout".equals(action)) {
            logout();
        }

        //
        String hashing=hash.toHash(password); //effettuaiamo l'hasing della password;

        //chiamiamo il metodo login per valitare se c'è corrispondenza tra le credenziali date in put e quelle nel db;
        UtenteBean b=new UtenteBean();

        try {
            b=login(email, hashing);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(b!=null){
            //se l'utente non è null, allora c'è corrispondeza e creiamo la sessione per l'utente;
            HttpSession session=request.getSession();
            session.setAttribute("utente", b);
            //una volta verificato l'utente, bisogna reindirizzarlo verso la sua area utente;

            //creiamo anche il carrello;
            Carrello cart=new Carrello(email);//passiamo la mail, in modo che venga associata a un utente in particolare;
            session.setAttribute("carrello", cart);

        }
        else{
            request.setAttribute("login-error", "Credenziali non valide");
            //bisogn richiedere all'utente di reiserire le credenziali;
        }

    }

    //adesso implementiamo i metodi di login e di logout;

    //dobbiamo passare al metodo l'hash della password inserita dall'utente, poichè non sono salvate in chiaro nel db;
    //il metodo login effettua una ricerca nel db della mail fornita dall'utente.
    //poi, confronta la password data in input con quella presente nel db;
    //se c'è corrispondenza, allora le credenziali sono corrette e viiene restituito l'oggetto UtenteBean. Altrimenti, restituisce null;
    public UtenteBean login(String email, String hashing) throws SQLException {

        //ricerchiamo l'email inserita dall'utente, tramite i metodo esposti nel dao corrispondente;
        UtenteBean b=new UtenteBean(); //
        b=utenti.doRetrieveByEmail(email);

        String p=b.getPassword(); //recuperiamo la password associata all'utente nel db;

        if(p.equals(hashing)){ //se la password inserita dall'utente al login corrisponde alla password associata alla sua email nel db, bisogna permettere l'accesso;
            return b;
        }
        return null; //se le credenziali non corrispondono, viene restituito null;
    }

    public boolean logout(){

        HttpServletRequest request = null;
        HttpSession session = request.getSession(false); // Recupera la sessione, non creare se non esiste

        if (session != null) {
            session.invalidate(); // Invalida la sessione se esiste
            return true;
        }

        return false; //se la invalidazione non viene eseguita
    }
}
