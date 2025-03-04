package main.java.dataManagement.bean;

import java.util.ArrayList;
import java.util.List;

public class OrdineBean {

    private int idOrdine;
    private String email;
    private float totale;
    private String metodoPagamento;
    private String indirizzo;


    public OrdineBean(){
        idOrdine = 0;
        email = null;
        metodoPagamento=null;
        indirizzo=null;
        totale=0;

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

}
