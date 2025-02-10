package main.java.application.gestioneCatalogo;

import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.dao.ProdottoDAO;

import javax.sql.DataSource;
import java.sql.SQLException;

//classe che permette la gestione dei prodotti nel catalogo;
public class Catalogo{
    ProdottoDAO dao;

    public Catalogo(DataSource ds){
        this.dao = new ProdottoDAO(ds);
    }


    //i prodotti sono salvati all'interno della tabella PRODOTTO.
    //catalogo si occupa della gestione dei prodotti


   //metodo per aggiungere prodotto al oggetto catalogo;
    public boolean addProduct(ProdottoBean prodotto) throws SQLException {
        //poichè ci viene passato direttamente il ProdottoBean
        //ci occupiamo di valutare solo se t
        // utti i dati inseriti sono corretti e di inserirlo nel db;

        boolean validazione=checkProduct(prodotto); //verifichiamo se i dati del prodotto sono corretti;
        boolean ris=false;

        if(validazione){
            ris=dao.doSave(prodotto);
        }

        return ris;
    }

    //metodo per rimuovere un prodotto dal catalogo
    public boolean removeProduct(ProdottoBean prodotto) throws SQLException {

        String nome= prodotto.getNome();
        boolean ris=dao.doDelete(nome);
        return ris;
    }

    public boolean checkProduct(ProdottoBean prodotto) {
        String nome = prodotto.getNome();
        float prezzo = prodotto.getPrezzo();
        int quantita = prodotto.getQuantita();
        String categoria = prodotto.getCategoria();

        // Controllo Nome (solo lettere e spazi)
        boolean n = nome.matches("[a-zA-Zà-ùÀ-Ù ]+");

        // Controllo Categoria (deve essere tra quelle valide)
        boolean c = categoria.equals("collane") || categoria.equals("bracciali") || categoria.equals("anelli");

        // Controllo Quantità (maggiore di 0)
        boolean q = quantita > 0;

        // Controllo Prezzo (tra 0 e 9999)
        boolean p = prezzo > 0 && prezzo <= 9999;

        // Se tutti i controlli sono validi, il prodotto è accettato
        boolean ris = n && c && q && p;

        return ris;
    }

}


