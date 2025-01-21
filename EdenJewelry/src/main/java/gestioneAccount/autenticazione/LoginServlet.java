package main.java.gestioneAccount.autenticazione;

import main.java.gestioneAccount.utente.UtenteDAO;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import main.java.utilities.HashingAlgoritm;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //usiamo questa servlet per gestire la richiesta di un utente di poter effettuare il login al sito e, in base ai dati inseriti,
    //restituaiamo una visione di questo consona al suo tipo (cioè seller o user);

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        // Inizializzazione del DAO per interagire con il database dei giochi
        // gameDAO = new Game_DAODataSource(getServletContext());
    }
private UtenteDAO utenti; //creiamo l'oggetto in grado di interagire che il contenuto del database;

    //questo perchè, una volta che ci vengono fortniti email e password, è necessario confrontare questi parametri con le varie entry del database alla ricerca di una corrispondenza;
    //se non vi è riscontro, bisogna informae l'utente che sta provando ad affettuare il login, che deve  reinserire le credenziali;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        //stabiliamo una connessione con il db, per mezzo del dao instanziato;
        DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");
        utenti=new UtenteDAO(ds);
        HashingAlgoritm ha=new HashingAlgoritm(); //creiamo un oggetto per poter
        //acquisiamo in ingresso i parametri dell'utente e valutiamo le se credenziali sono corrette;
        String email = request.getParameter("email");
        //System.out.println("email" + email);

        String password = request.getParameter("password");
        //System.out.println("password" + password);
        String hashedPassword=toHash(password);
    }
}
