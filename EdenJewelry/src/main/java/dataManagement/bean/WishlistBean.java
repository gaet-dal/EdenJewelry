package main.java.dataManagement.bean;

import java.util.List;
import java.util.ArrayList;

public class WishlistBean {
    private int idWishlist;
    private String email;


    public WishlistBean() {
        idWishlist = 0;
        email = null;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdWishlist() {
        return idWishlist;
    }

    public void setIdWishlist(int idWishlist) {
        this.idWishlist = idWishlist;
    }
}