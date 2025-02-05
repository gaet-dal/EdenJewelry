package main.java.presentation.areaRiservata;

import main.java.application.gestioneAccount.GestioneRegistrazione;
import main.java.dataManagement.bean.UtenteBean;
import main.java.dataManagement.dao.UtenteDAO;
import main.java.utilities.HashingAlgoritm;

import javax.servlet.RequestDispatcher;
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

        GestioneRegistrazione gest=new GestioneRegistrazione();
        boolean n=gest.checkNomeCognome(nome);
        boolean c= gest.checkNomeCognome(cognome);
        boolean e= gest.checkEmail(email);

        //se i campi controllati rispettano le condizioni dei metodi di validazione, possiamo procedere con la registrazione
        if(n==true && c==true && e==true){
            HashingAlgoritm hash=new HashingAlgoritm();
            String hashing= hash.toHash(password); //effettuiamo l'hash della password;

            try {
                boolean RisultatoRegistrazione= gest.register(nome, cognome, email, hashing, tipo, utenti);

                if(RisultatoRegistrazione==true){
                    //la registazione è andata a buon fine;
                    //bisogna creare la sessione e rimandare l'utente sulla home;

                }
                else{
                    //registrazione non andata a buon fine;
                    request.setAttribute("register-error", "registrazione non andata a buon fine");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/script/registrazione.jsp");
                    dispatcher.forward(request, response);
                }


            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
        else if (n==false){
            request.setAttribute("nome-error", "il nome deve contenere solo lettere dell'alfabeto");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/script/registrazione.jsp");
            dispatcher.forward(request, response);
        }
        else if (c==false){
            request.setAttribute("cognome-error", "il cognome deve contenere solo lettere dell'alfabeto");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/script/registrazione.jsp");
            dispatcher.forward(request, response);
        }
        else if (e==false){
            request.setAttribute("email-error", "l'email non valida");
            //mandiamo gli errori sulla jsp;
            RequestDispatcher dispatcher = request.getRequestDispatcher("/script/registrazione.jsp");
            dispatcher.forward(request, response);
        }
    }
}
