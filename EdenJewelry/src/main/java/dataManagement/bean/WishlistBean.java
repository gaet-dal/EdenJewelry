package main.java.dataManagement.bean;

import java.util.List;
import java.util.ArrayList;

public class WishlistBean {
    private String email;
    private List<ProdottoBean> prodotti;

    public WishlistBean() {
        email = null;
        prodotti = new ArrayList<ProdottoBean>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ProdottoBean> getProdotti() {
        return prodotti;
    }

    public void addProdotto(ProdottoBean p){
        prodotti.add(p);
    }

    public void removeProdotto(String nome){
        ProdottoBean p= new ProdottoBean();
        p.setNome(nome);

        prodotti.remove(p);
    }
}