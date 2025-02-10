package main.java.application.gestioneAccount;

import main.java.dataManagement.bean.UtenteBean;
import main.java.dataManagement.dao.UtenteDAO;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class GestioneAutenticazione {
    //adesso implementiamo i metodi di login e di logout;

    //dobbiamo passare al metodo l'hash della password inserita dall'utente, poichè non sono salvate in chiaro nel db;
    //il metodo login effettua una ricerca nel db della mail fornita dall'utente.
    //poi, confronta la password data in input con quella presente nel db;
    //se c'è corrispondenza, allora le credenziali sono corrette e viiene restituito l'oggetto UtenteBean. Altrimenti, restituisce null;
    public UtenteBean login(String email, String hashing, UtenteDAO utenti) throws SQLException {

        //ricerchiamo l'email inserita dall'utente, tramite i metodo esposti nel dao corrispondente;
        UtenteBean b=utenti.doRetrieveByEmail(email);

        if (b == null)
            return null;

        String p=b.getPassword(); //recuperiamo la password associata all'utente nel db;

        if(p.equals(hashing)){ //se la password inserita dall'utente al login corrisponde alla password associata alla sua email nel db, bisogna permettere l'accesso;
            return b;
        }
        return null; //se le credenziali non corrispondono, viene restituito null;
    }

    public boolean logout(HttpSession session) {
        if (session != null) {
            session.invalidate(); // Invalida la sessione se esiste
            return true;
        }

        return false; //se la invalidazione non viene eseguita
    }
}
