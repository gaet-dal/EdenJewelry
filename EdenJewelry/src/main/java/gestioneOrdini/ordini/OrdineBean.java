package main.java.gestioneOrdini.ordini;

import main.java.gestioneProdotti.prodotto.ProdottoBean;

import java.util.List;

public class OrdineBean {

    private int idOrdine;
    private String email;
    private float totale;
    private String metodoPagamento;
    private String indirizzo;
    private List<ProdottoBean> prodotti;

    public OrdineBean(){
        idOrdine = 0;
        email = null;
        metodoPagamento=null;
        indirizzo=null;
        totale=0;
        prodotti=null; //assegniamo la lista di prodotti che recuperiamo

    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public String getEmail() {//l'email non deve essere inizializzata nel costruttore, perchè noi recuperiamo quella che l'utente ha usato per la registrazione
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public List <ProdottoBean> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<ProdottoBean> prodotti) {
        this.prodotti = prodotti;
    }

    public void addProdotti(ProdottoBean prodotto) {
        prodotti.add(prodotto);
    }

    public void removeProdotti(ProdottoBean prodotto) {
        prodotti.remove(prodotto);
    }

}
