package main.java.application.gestioneAcquisti;

import main.java.dataManagement.bean.ItemWishlistBean;
import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.bean.WishlistBean;
import main.java.dataManagement.dao.ItemWishlistDAO;
import main.java.dataManagement.dao.ProdottoDAO;
import main.java.dataManagement.dao.WishlistDAO;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class Wishlist {

    public boolean aggiungiWishlist(String nome, String email, DataSource ds) {
        //usiamo il nome del prodotto (chiave primaria) per recuoerare tuttu i suoi dati dal db;

        if(ds==null) System.out.println("DataSource è null nella wrapper");
        else System.out.println("DataSource NON è null nella wrapper");

        System.out.println("Nome aggiungi wishlist: " + nome); //nome del prodotto;
        System.out.println("email aggiungi wishlist: " + email);

        ProdottoDAO prodottoDAO=new ProdottoDAO(ds);
        WishlistDAO wishlistDAO = new WishlistDAO(ds);
        ItemWishlistDAO itemWishlistDAO = new ItemWishlistDAO(ds);

        ProdottoBean prodotto;
        try {
            prodotto = prodottoDAO.doRetrieveByNome(nome);
            System.out.println("Prodotto aggiungi wishlist: " + prodotto.toString()); //questo viene trovato correttamente;
        } catch (SQLException e) {
            System.out.println("Prodotto non esiste");
            //il prodotto non esiste
            return false;
        }

        boolean aggiungi=false;
        try {
            WishlistBean wishlist = wishlistDAO.doRetrieveByEmail(email);
            System.out.println("Wishlist aggiungi wishlist: " + wishlist.toString()); //wishlist viene trovata correttamente;

            ItemWishlistBean item = new ItemWishlistBean();
            item.setIdWishlist(wishlist.getIdWishlist());
            System.out.println("Item aggiungi wishlist: " + item.toString());
            item.setNomeProdotto(nome);

            System.out.println("Item aggiungi wishlist: "+item.toString());

            aggiungi = itemWishlistDAO.doSave(item);
            if(aggiungi==false) {
                System.out.println("Aggiungi alla wishlist non funziona");
            }
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

    public List<ItemWishlistBean> viewWishList(String email, DataSource ds) throws SQLException {
        ProdottoDAO prodottoDAO=new ProdottoDAO(ds);
        WishlistDAO wishlistDAO = new WishlistDAO(ds);
        ItemWishlistDAO itemWishlistDAO = new ItemWishlistDAO(ds);

        WishlistBean wishlist = wishlistDAO.doRetrieveByEmail(email); //ricerchiamo la entry corrispondente e recuperiamo l'id della wishlist;
        int idWishlist=wishlist.getIdWishlist();

        //adesso facciamo una stessa ricerca in itemWishList e ci salviamo una list di item con lo stesso idWishlist;
        List<ItemWishlistBean> list= itemWishlistDAO.doRetrieveByIdWishlist(idWishlist);

        return list;
    }
}
