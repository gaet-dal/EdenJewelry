package main.java.application.gestioneAcquisti;

import main.java.dataManagement.bean.OrdineBean;
import main.java.dataManagement.bean.RigaOrdineBean;
import main.java.dataManagement.dao.OrdineDAO;
import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.dao.ProdottoDAO;
import main.java.dataManagement.dao.RigaOrdineDAO;
import main.java.dataManagement.dao.UtenteDAO;

import javax.sql.DataSource;
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

    public static boolean checkout(Carrello carrello, String email, String metodoPagamento, String indirizzo, DataSource ds){
        // I dati non sono validi, non si può proseguire con il checkout
        if (!checkIndirizzo(indirizzo) || checkMetodoPagamento(metodoPagamento) || carrello.isEmpty())
            return false;

        OrdineDAO ordini=new OrdineDAO(ds);
        ProdottoDAO prodotti =new ProdottoDAO(ds);
        RigaOrdineDAO righe =  new RigaOrdineDAO(ds);
        OrdineBean ordine = new OrdineBean();
        float totale = 0.0f;

        //recuperiamo l'ultimo ordine effettuato;
        ordine= ordini.doRetrieveUltimoOrdine();

        int numeroOrdine=0;
        if(ordine!=null){

            numeroOrdine=ordine.getIdOrdine();
            numeroOrdine+=1; //creiamo il l'indentificativo dell'oridne che dovvrà essere effettuato e salvato nel db;
        }


        List<ItemCarrello> list = carrello.getListProdotti();
        ItemCarrello []arr= list.toArray(new ItemCarrello[0]);

        for(ItemCarrello it : arr) {
            String nome = it.getNome();
            ProdottoBean p;

            RigaOrdineBean riga = new RigaOrdineBean();

            try{
                p = prodotti.doRetrieveByNome(nome);
            } catch (SQLException e) {
                logger.warning("Errore nel fetch del prodotto");
                throw new RuntimeException(e);
            }

            riga.setNumeroOrdine(numeroOrdine);
            riga.setNomeProdotto(nome);
            riga.setQuantità(it.getQuantità());


            totale += p.getPrezzo() * it.getQuantità();
            riga.setPrezzoUnitario(p.getPrezzo());
            righe.doSave(riga); //salviamo la riga nel db;

        }

        //salvataggio dei dati nel bean per la memorizazzione
        ordine.setIndirizzo(indirizzo);
        ordine.setTotale(totale);
        ordine.setEmail(email);
        ordine.setMetodoPagamento(metodoPagamento);

        boolean result = false;

        try {
            result = ordini.doSave(ordine);
        } catch (SQLException e) {
            logger.warning("Errore nel salvataggio dell'ordine");
            e.printStackTrace();
        }

        return result;
    }
}
