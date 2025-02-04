package main.java.application.gestioneCatalogo;

import main.java.gestioneProdotti.homepage.Strategy;
import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.dao.ProdottoDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Gaetano D'Alessio
 * Implementazione banale dell'interfaccia Strategy.
 * Semplicemente, cerca la presenza della stringa in un nome di prodotto
 * recuperato dal database.
 * @see Strategy
 */

public class SimpleSearch implements Strategy{
    private static Logger logger;

    @Override
    public List<ProdottoBean> search(String query, ProdottoDAO prodotti) {
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

            if(prodotto.getNome().toLowerCase().contains(query.toLowerCase()))
                l2.add(prodotto);
        }

        return l2;
    }
}
