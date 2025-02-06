package main.java.application.gestioneAcquisti;

import main.java.dataManagement.bean.ItemWishlistBean;
import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.bean.WishlistBean;
import main.java.dataManagement.dao.ItemWishlistDAO;
import main.java.dataManagement.dao.ProdottoDAO;
import main.java.dataManagement.dao.WishlistDAO;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Wishlist {
    public boolean aggiungiWishlist(String nome, String email, DataSource ds) {
        //usiamo il nome del prodotto (chiave primaria) per recuoerare tuttu i suoi dati dal db;
        ProdottoDAO prodottoDAO=new ProdottoDAO(ds);
        WishlistDAO wishlistDAO = new WishlistDAO(ds);
        ItemWishlistDAO itemWishlistDAO = new ItemWishlistDAO(ds);
        ProdottoBean prodotto;
        try {
            prodotto = prodottoDAO.doRetrieveByNome(nome);
        } catch (SQLException e) {
            //il prodotto non esiste
            return false;
        }

        boolean aggiungi=false;
        try {
            WishlistBean wishlist = wishlistDAO.doRetrieveByEmail(email);
            ItemWishlistBean item = new ItemWishlistBean();
            item.setIdWishlist(wishlist.getIdWishlist());
            item.setNomeProdotto(nome);

            aggiungi = itemWishlistDAO.doSave(item);
        } catch (SQLException e) {
            return false;
        }

        return aggiungi; //restituiiamo l'esito dell'operazione doSave;
    }

    public boolean removeWishlist(int idItem, DataSource ds) throws SQLException {
        ItemWishlistDAO itemWishlistDAO = new ItemWishlistDAO(ds);
        boolean rimuovi=false;

        //recuperiamo la lista salvata nel db e manteniamola in un oggetto WishlistBean;

        rimuovi = itemWishlistDAO.doDelete(idItem);

        return rimuovi;
    }
}
