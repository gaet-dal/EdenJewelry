package main.java.application.gestioneAcquisti;

import main.java.dataManagement.bean.ProdottoBean;

import java.util.HashMap;
import java.util.Map;

import java.util.logging.Logger;

public class Carrello {
    private Map<ProdottoBean, Integer> prodotti;
    private String email;

    private static Logger log = Logger.getLogger(Carrello.class.getName());

    public Carrello(String email) {
        prodotti = new HashMap<ProdottoBean, Integer>();
        this.email = email;

        log.info("Creato carrello per " + email);
    }

    public void addToCart(ProdottoBean p){
        if(p == null)
            throw new NullPointerException("ProdottoBean is null");

        if (prodotti.containsKey(p)){
            prodotti.put(p, prodotti.get(p) + 1);
            log.info("Incrementata quantit à" + p.getNome());
        } else {
            prodotti.put(p, 1);
            log.info("Aggiunto " + p.getNome());

        }
    }

    public void deleteFromCart(ProdottoBean p){
        if(p == null)
            throw new NullPointerException("ProdottoBean is null");

        if (prodotti.containsKey(p)){
            prodotti.remove(p);
            log.info("Rimosso " + p.getNome() + " dal carrello");
        }
    }

    public void modificaQuantità(ProdottoBean p, int q){
        if(p == null)
            throw new NullPointerException("ProdottoBean is null");

        if (q <= 0) {
            log.warning("La quantità non può essere <= 0");
            return;
        }


        if(prodotti.containsKey(p)){
            prodotti.put(p, q);
        }
    }

    public Map<ProdottoBean, Integer> getMapProdotti(){
        return prodotti;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmpty() {
        return prodotti.isEmpty();
    }
}
