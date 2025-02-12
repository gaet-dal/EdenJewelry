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
import java.util.ArrayList;
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
        boolean ris=false;
        if (indirizzo == null || indirizzo.isEmpty()) {
            ris= false;
        }


        for (char c : indirizzo.toCharArray()) {
            if (Character.isDigit(c) || Character.isWhitespace(c) || Character.isLetter(c) ) {
                ris= true;
            }
        }

        return ris; // Se arriva qui, tutti i caratteri sono validi
    }


    private static boolean checkMetodoPagamento(String metodoPagamento){
       boolean ris = false;

        if (metodoPagamento == null || metodoPagamento.isEmpty()) {
            ris= false;
        }

        // Controlla se la stringa contiene numeri o spazi
        for (char c : metodoPagamento.toCharArray()) {
            if (Character.isDigit(c) || Character.isWhitespace(c)) {
                ris= true;
            }
        }

        return ris;
    }

    public static boolean checkout(Carrello carrello, String email, String metodoPagamento, String indirizzo, DataSource ds){
        // I dati non sono validi, non si può proseguire con il checkout
        if (!checkIndirizzo(indirizzo) || !checkMetodoPagamento(metodoPagamento) || carrello.isEmpty()) {
            System.out.println("checkIndirizzo "+checkIndirizzo(indirizzo));
            System.out.println("checkMetodoPagamento "+checkMetodoPagamento(metodoPagamento));
            System.out.println("carrello "+ carrello.toString());

            System.out.println("Errore nei dati di checkout: indirizzo/metodoPagamento non valido o carrello vuoto.");
            return false;
        }


        OrdineDAO ordini=new OrdineDAO(ds);
        ProdottoDAO prodotti =new ProdottoDAO(ds);
        RigaOrdineDAO righe =  new RigaOrdineDAO(ds);
        OrdineBean ordine = new OrdineBean();
        float totale = 0.0f;

        //recuperiamo l'ultimo ordine effettuato;
        ordine= ordini.doRetrieveUltimoOrdine();

        int numeroOrdine=1;
        if(ordine!=null){

            numeroOrdine=ordine.getIdOrdine();
            System.out.println("numeroOrdine "+numeroOrdine);
            numeroOrdine+=1; //creiamo il l'indentificativo dell'oridne che dovvrà essere effettuato e salvato nel db;
        }


        List<ItemCarrello> list = carrello.getListProdotti();
        ItemCarrello []arr= list.toArray(new ItemCarrello[0]);
        List<RigaOrdineBean> righeToSave =new ArrayList<>();

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
            righeToSave.add(riga);

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

        Iterator<RigaOrdineBean> it = righeToSave.iterator();
        while(it.hasNext()) {
            RigaOrdineBean riga = it.next();
            righe.doSave(riga);
        }

        return result;
    }
}
