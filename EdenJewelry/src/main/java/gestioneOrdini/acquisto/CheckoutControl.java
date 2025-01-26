package main.java.gestioneOrdini.acquisto;

import main.java.gestioneOrdini.ordini.OrdineBean;
import main.java.gestioneOrdini.ordini.OrdineDAO;
import main.java.gestioneProdotti.carrello.Carrello;
import main.java.gestioneProdotti.prodotto.ProdottoBean;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Gaetano D'Alessio
 * Classe che gestisce l'operazione di checkout, comprendente anche i metodi
 * di controllo ai valori in input.
 */

public class CheckoutControl {
    private static Logger logger = Logger.getLogger(CheckoutControl.class.getName());

    public boolean checkIndirizzo(String indirizzo) {
        if (indirizzo == null || indirizzo.equals(""))
            return false;

        boolean ris = false;
        for(int i = 0; i < indirizzo.length(); i++) {
            char lettera = indirizzo.toLowerCase().charAt(i);

            if((lettera >= 'a' && lettera <= 'z') || (lettera >= '0' && lettera <= '9'))
                ris = true;
            else
                return false;
        }

        return ris;
    }

    public boolean checkMetodoPagamento(String metodoPagamento){
        if (metodoPagamento == null || metodoPagamento.equals(""))
            return false;

        //hardcoding dei valori per semplicità
        switch(metodoPagamento){
            case "contanti", "paypal", "carta di credito":
                return true;
            default:
                return false;

        }
    }

    public boolean checkout(Carrello carrello, String metodoPagamento, String indirizzo, String email, OrdineDAO dao){
        // I dati non sono validi, non si può proseguire con il checkout
        if (!checkIndirizzo(indirizzo) || checkMetodoPagamento(metodoPagamento) || carrello.isEmpty())
            return false;

        OrdineBean ordine = new OrdineBean();
        Map<ProdottoBean, Integer> map = carrello.getMapProdotti();
        Iterator<ProdottoBean> iterator = map.keySet().iterator();
        float totale = 0.0f;

        //aggiunta dei prodotti e calcolo del totale
        while(iterator.hasNext()){
            ProdottoBean prodotto = iterator.next();

            int i = map.get(prodotto);
            while(i >= 0) {
                ordine.addProdotti(prodotto);
                totale += prodotto.getPrezzo();
                i--;
            }
        }

        //salvataggio dei dati nel bean per la memorizazzione
        ordine.setIndirizzo(indirizzo);
        ordine.setTotale(totale);
        ordine.setEmail(email);
        ordine.setMetodoPagamento(metodoPagamento);

        boolean result = false;

        try {
            result = dao.doSave(ordine);
        } catch (SQLException e) {
            logger.warning("Errore nel salvataggio dell'ordine");
            e.printStackTrace();
        }

        return result;
    }
}
