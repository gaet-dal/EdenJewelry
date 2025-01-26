package main.java.gestioneProdotti.wishlist;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.gestioneAccount.utente.UtenteBean;
import main.java.gestioneProdotti.prodotto.ProdottoBean;

import javax.sql.DataSource;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import java.util.List;


public class WishlistDAO {
    private static DataSource ds;

    Logger logger = Logger.getLogger(WishlistDAO.class.getName());

    private static final String TABLE_NAME = "WISHLIST";

    public WishlistDAO(DataSource ds) {
        this.ds = ds;

        if(ds == null) {
            logger.info("DataSource wishlist nullo");
        } else {
            logger.info("DataSource wishlist istanziato correttamente");
        }
    }

    public synchronized boolean doSave(WishlistBean wishlist) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (email, prodotti) VALUES (?, ?)";

        Gson gson = new Gson();
        String listaProdotti;

        listaProdotti = gson.toJson(wishlist.getProdotti());

        try{
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, wishlist.getEmail());
            preparedStatement.setString(2, listaProdotti);

            result = preparedStatement.executeUpdate();
        }  finally {
            closeResources(preparedStatement, connection);
        }

        return result != 0;
    }

    public synchronized boolean doDelete(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE email = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);

            preparedStatement.setString(1, email);

            result = preparedStatement.executeUpdate();
        } finally{
            closeResources(preparedStatement, connection);
        }

        return result != 0;
    }

    public synchronized List<WishlistBean> doRetrieveAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<WishlistBean> wishlists = new ArrayList<WishlistBean>();

        int result = 0;

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        try{
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                WishlistBean bean = new WishlistBean();

                bean.setEmail(rs.getString("email"));
                String listaDB = rs.getString("prodotti");

                Gson gson = new Gson();
                Type collectionType = new TypeToken<List<ProdottoBean>>(){}.getType();
                List<ProdottoBean> listaProdotti = gson.fromJson(listaDB, collectionType);

                Iterator<ProdottoBean> it = listaProdotti.iterator();
                while (it.hasNext()) {
                    bean.addProdotto(it.next());
                }

                wishlists.add(bean);
            }
            rs.close();
        } finally {
            closeResources(preparedStatement, connection);
        }

        return wishlists;
    }

    public synchronized WishlistBean doRetrieveByEmail(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        WishlistBean bean = new WishlistBean();

        int result = 0;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";

        try{
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            bean.setEmail(rs.getString("email"));
            String listaDB = rs.getString("prodotti");

            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<ProdottoBean>>(){}.getType();
            List<ProdottoBean> listaProdotti = gson.fromJson(listaDB, collectionType);

            Iterator<ProdottoBean> it = listaProdotti.iterator();
            while (it.hasNext()) {
                bean.addProdotto(it.next());
            }
            rs.close();
        } finally {
            closeResources(preparedStatement, connection);
        }

        return bean;

    }

    private void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    logger.warning("Errore nella chiusura delle risorse");
                }
            }
        }
    }
}