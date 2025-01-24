package main.java.gestioneProdotti.homepage;

import main.java.gestioneProdotti.prodotto.ProdottoBean;
import main.java.gestioneProdotti.prodotto.ProdottoDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Gaetano D'Alessio
 * Implementazione dell'interfaccia Strategy, che effettua la ricerca
 * per categoria e non per nome
 * @see Strategy
 */

public class CategorySearch implements Strategy {
    private static Logger logger;

    @Override
    public List<ProdottoBean> search(String category, ProdottoDAO prodotti){
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
        while(iterator.hasNext()){
            ProdottoBean p = iterator.next();

            if (p.getCategoria().toLowerCase().contains(category.toLowerCase()))
                l2.add(p);
        }

        return l2;
    }
}
