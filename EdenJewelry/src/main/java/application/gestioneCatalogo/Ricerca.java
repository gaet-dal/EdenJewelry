package main.java.application.gestioneCatalogo;

import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.dao.ProdottoDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Gaetano D'Alessio
 * Classe che gestisce la ricerca di prodotti nel catalogo,
 * fornendo una parola chiave. Quest’ultima, viene confrontata
 * con i nomi dei prodotti. Vengono restituiti tutti i prodotti
 * del catalogo che presentano una corrispondenza (cioè, la stringa
 * in input corrisponde ad una porzione di nome di uno o più prodotti).
 * Se la stringa categoria è presente, la ricerca viene effettuata in
 * un sottoinsieme più piccolo.
 */

public class Ricerca{
    private static Logger logger = Logger.getLogger(Ricerca.class.getName());


    public List<ProdottoBean> search(String query, String cat,ProdottoDAO prodotti) {
        List<ProdottoBean> l1;
        List<ProdottoBean> l2 = new ArrayList<ProdottoBean>();

        try {
            l1 = prodotti.doRetrieveAll();
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            logger.warning("Errore durante il fetch dei prodotti");
            throw new RuntimeException(e);
        }

        Iterator<ProdottoBean> iterator = l1.iterator();

        while(iterator.hasNext()) {
            ProdottoBean prodotto = iterator.next();

            if(prodotto.getNome().toLowerCase().contains(query.toLowerCase())) {
                if(prodotto.getCategoria().equalsIgnoreCase(cat) || cat ==null) {
                    l2.add(prodotto);
                }
            }
        }

        return l2;
    }
}
