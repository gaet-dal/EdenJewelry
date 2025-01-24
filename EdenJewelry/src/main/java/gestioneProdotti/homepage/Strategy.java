package main.java.gestioneProdotti.homepage;

import main.java.gestioneProdotti.prodotto.ProdottoBean;
import main.java.gestioneProdotti.prodotto.ProdottoDAO;

import java.util.List;

/**
 * @author Gaetano D'Alessio
 * Interfaccia alla base dello Strategy Design Pattern.
 * Grazie ad esso, abbiamo implementato modi diversi di eseguire la ricerca a runtime.
 */

public interface Strategy {
    public List<ProdottoBean> search(String query, ProdottoDAO prodotti);
}
