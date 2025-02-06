package main.java.application.gestioneAcquisti;

import main.java.dataManagement.bean.OrdineBean;
import main.java.dataManagement.bean.RigaOrdineBean;
import main.java.dataManagement.dao.OrdineDAO;
import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.dao.ProdottoDAO;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Gaetano D'Alessio
 * Classe che gestisce l'operazione di checkout, comprendente anche i metodi
 * di controllo ai valori in input.
 */

public class CheckoutControl {
    private static Logger logger = Logger.getLogger(CheckoutControl.class.getName());

    private static boolean checkIndirizzo(String indirizzo) {
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

    private static boolean checkMetodoPagamento(String metodoPagamento){
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

    public static boolean checkout(Carrello carrello, String metodoPagamento, String indirizzo, String email, OrdineDAO dao, ProdottoDAO prodotti){
        // I dati non sono validi, non si può proseguire con il checkout
        if (!checkIndirizzo(indirizzo) || checkMetodoPagamento(metodoPagamento) || carrello.isEmpty())
            return false;

        OrdineBean ordine = new OrdineBean();
        float totale = 0.0f;

        List<ItemCarrello> list = carrello.getListProdotti();
        ItemCarrello []arr= list.toArray(new ItemCarrello[0]);

        for(ItemCarrello it : arr) {
            String nome = it.getNome();
            ProdottoBean p;
            RigaOrdineBean riga = new RigaOrdineBean();

            try{
                p = prodotti.doRetrieveByNome(nome);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            totale += p.getPrezzo() * it.getQuantità();
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
