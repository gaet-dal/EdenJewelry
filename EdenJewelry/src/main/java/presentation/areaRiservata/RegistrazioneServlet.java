package main.java.presentation.areaRiservata;

import main.java.application.gestioneAccount.GestioneRegistrazione;
import main.java.application.gestioneAcquisti.Carrello;
import main.java.dataManagement.bean.UtenteBean;
import main.java.dataManagement.dao.UtenteDAO;
import main.java.utilities.HashingAlgoritm;

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
import java.util.logging.Logger;

public class RegistrazioneServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(RegistrazioneServlet.class.getName());

    private static final long serialVersionUID = 1L;

    //servlet che utilizziamo per gestire la registrazione di un utente;
    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);

    }
    private UtenteDAO utenti;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        //creiamo il dao per poter lavorare con il database;
        DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");
        utenti=new UtenteDAO(ds);

        String nome = request.getParameter("nome");

        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String tipo=request.getParameter("tipo");

        GestioneRegistrazione gest=new GestioneRegistrazione(utenti);
        boolean n=gest.checkNomeCognome(nome);
        boolean c= gest.checkNomeCognome(cognome);
        boolean e= gest.checkEmail(email);




        //aggiungiamo un controllo sull'email per vedere se esiste già nel db, prima di arrivare ad avere un eccezione sul db stesso;


        //se i campi controllati rispettano le condizioni dei metodi di validazione, possiamo procedere con la registrazione
        if(n==true && c==true && e==true){
            HashingAlgoritm hash=new HashingAlgoritm();
            String hashing= hash.toHash(password); //effettuiamo l'hash della password;

            try {
                boolean RisultatoRegistrazione= gest.register(nome, cognome, email, hashing, tipo);

                if(RisultatoRegistrazione==true){
                    //la registazione è andata a buon fine;
                    //bisogna creare la sessione e rimandare l'utente sulla home;
                    UtenteBean utente= utenti.doRetrieveByEmail(email);
                    HttpSession session=request.getSession();
                    session.setAttribute("utente", utente);
                    //una volta verificato l'utente, bisogna reindirizzarlo verso la sua area utente;

                    //creiamo anche il carrello;
                    Carrello cart=new Carrello(email);//passiamo la mail, in modo che venga associata a un utente in particolare;
                    session.setAttribute("carrello", cart);

                } else{
                    request.setAttribute("register-error", "registrazione non andata a buon fine");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/script/registazione.jsp");
                    dispatcher.forward(request, response);
                }


            } catch (SQLException ex) {
               logger.warning("Un utente già registrato ha tentato la registrazione");
                request.setAttribute("email-error", "email già esistente");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/script/registazione.jsp");
                dispatcher.forward(request, response);
            }

        }
        else if (n==false){
            request.setAttribute("nome-error", "il nome deve contenere solo lettere dell'alfabeto");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/script/registazione.jsp");
            dispatcher.forward(request, response);
        }
        else if (c==false){
            request.setAttribute("cognome-error", "il cognome deve contenere solo lettere dell'alfabeto");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/script/registazione.jsp");
            dispatcher.forward(request, response);
        }
        else if (e==false){
            request.setAttribute("email-error", "l'email non è valida");
            //mandiamo gli errori sulla jsp;
            RequestDispatcher dispatcher = request.getRequestDispatcher("/script/registazione.jsp");
            dispatcher.forward(request, response);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/script/homepage.jsp");
        dispatcher.forward(request, response);
    }
}
