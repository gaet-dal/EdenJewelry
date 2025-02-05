package main.java.dataManagement.bean;

public class ItemWishlistBean {
    private int idItem;
    private int idWishlist;
    private String nomeProdotto;

    public ItemWishlistBean() {
        idItem = 0;
        idWishlist = 0;
        nomeProdotto = null;
    }

    public int getIdItem() {
        return idItem;
    }
    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getIdWishlist() {
        return idWishlist;
    }
    public void setIdWishlist(int idWishlist) {
        this.idWishlist = idWishlist;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }
}
