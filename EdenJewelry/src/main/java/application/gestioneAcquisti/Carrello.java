package main.java.application.gestioneAcquisti;

import main.java.dataManagement.bean.ProdottoBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.function.LongUnaryOperator;
import java.util.logging.Logger;

public class Carrello {
    private List<ItemCarrello> prodotti;
    private String email;

    private static Logger log = Logger.getLogger(Carrello.class.getName());

    public Carrello(String email) {
        prodotti = new ArrayList<ItemCarrello>();
        this.email = email;

        log.info("Creato carrello per " + email);
    }

    public void addToCart(String nome){
        if(nome == null)
            throw new NullPointerException("Nome is null");

        ItemCarrello it = new ItemCarrello(nome, 1);

        if(prodotti.contains(it)) {
            prodotti.remove(it);
            it.setQuantità(it.getQuantità()+ 1);
            prodotti.add(it);
            log.info("Incrementata quantità" + nome);
        } else {
            prodotti.add(it);
            log.info("Aggiunto " + nome);
        }
    }

    public void deleteFromCart(String nome){
        if(nome == null)
            throw new NullPointerException("Nome is null");

        ItemCarrello it = new ItemCarrello(nome, 1);

        if (prodotti.contains(it)) {
            prodotti.remove(it);
            log.info("Rimosso " + nome + " dal carrello");
        }
    }

    public void modificaQuantità(String nome, int q){
        if(nome == null)
            throw new NullPointerException("ProdottoBean is null");

        if (q <= 0) {
            log.warning("La quantità non può essere <= 0");
            return;
        }

        ItemCarrello it = new ItemCarrello(nome, 1);


        if(prodotti.contains(it)) {
            prodotti.remove(it);
            prodotti.add(new ItemCarrello(nome, q));
            log.info("Modificata quantità " + nome);
        }
    }

    public List<ItemCarrello> getListProdotti(){
        return prodotti;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmpty() {
        return prodotti.isEmpty();
    }
}
