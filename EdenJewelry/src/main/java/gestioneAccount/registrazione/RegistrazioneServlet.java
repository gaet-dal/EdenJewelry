package main.java.gestioneAccount.registrazione;

import main.java.gestioneAccount.utente.UtenteBean;
import main.java.gestioneAccount.utente.UtenteDAO;
import main.java.utilities.HashingAlgoritm;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class RegistrazioneServlet extends HttpServlet {

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

        gestioneRegistrazione gest=new gestioneRegistrazione();
        boolean n=gest.checkNomeCognome(nome);
        boolean c= gest.checkNomeCognome(cognome);
        boolean e= gest.checkEmail(email);

        //se i campi controllati rispettano le condizioni dei metodi di validazione, possiamo procedere con la registrazione
        if(n==true && c==true && e==true){
            HashingAlgoritm hash=new HashingAlgoritm();
            String hashing= hash.toHash(password); //effettuiamo l'hash della password;

            try {
                boolean RisultatoRegistrazione= register(nome, cognome, email, hashing, tipo);

                if(RisultatoRegistrazione==true){
                    //la registazione è andata a buon fine;
                    //bisogna creare la sessione e rimandare l'utente sulla home;

                }
                else{
                    //registrazione non andata a buon fine;
                    request.setAttribute("register-error", "registrazione non andata a buon fine");
                }


            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
        else if (n==false){
            request.setAttribute("register-error", "il nome deve contenere solo lettere dell'alfabeto");
        }
        else if (c==false){
            request.setAttribute("register-error", "il cognome deve contenere solo lettere dell'alfabeto");
        }
        else if (e==false){
            request.setAttribute("register-error", "l'email non valida");
        }





    }

    public boolean register(String nome, String cognome, String email, String hashing, String tipo) throws SQLException {
        DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");
        utenti=new UtenteDAO(ds);

        //creiamo un UtenteBean in cui inserire i parametri presi dall'utente ed inserirli nel db;

        UtenteBean utentebean=new UtenteBean();

        utentebean.setNome(nome);
        utentebean.setCognome(cognome);
        utentebean.setEmail(email);
        utentebean.setPassword(hashing); //salviamo l'hash della password per motivi di sicurezza;
        utentebean.setTipo(tipo);

        boolean RisultatoRegistrazione= utenti.doSave(utentebean); //salviamo il nuovo utente nel db

    return RisultatoRegistrazione;
    }
}
