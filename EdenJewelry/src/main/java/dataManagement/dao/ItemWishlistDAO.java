package main.java.dataManagement.dao;

import main.java.dataManagement.bean.ItemWishlistBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ItemWishlistDAO {
    private static DataSource ds;
    private static Logger logger = Logger.getLogger(ItemWishlistDAO.class.getName());
    private static final String TABLE_NAME = "ITEMWISHLIST";

    public ItemWishlistDAO(DataSource ds){
        this.ds = ds;

        if(ds == null) {
            logger.info("DataSource itemwishlist nullo"); //connessione non creata;
        } else {
            logger.info("DataSource wishlist istanziato correttamente"); //connessione creata correttamente
        }
    }

    public synchronized boolean doSave(ItemWishlistBean item) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;

        String insertSQL = "INSERT INTO " + TABLE_NAME + " (idWishlist, nomeProdotto) VALUES (?, ?)";

        try {
            // Log: Inizio operazione
            System.out.println("Inizio operazione di salvataggio per l'item nella wishlist.");

            connection = ds.getConnection();
            System.out.println("Connessione al database stabilita.");

            preparedStatement = connection.prepareStatement(insertSQL);
            System.out.println("Query preparata: " + insertSQL);

            // Impostiamo i parametri
            preparedStatement.setInt(1, item.getIdWishlist());
            preparedStatement.setString(2, item.getNomeProdotto());

            // Log: Parametri della query
            System.out.println("Parametri impostati: idWishlist = " + item.getIdWishlist() + ", nomeProdotto = " + item.getNomeProdotto());

            // Eseguiamo l'aggiornamento
            result = preparedStatement.executeUpdate();
            System.out.println("Eseguito executeUpdate, result = " + result);

        } catch (SQLException e) {
            // Log: Errore durante l'esecuzione
            System.out.println("Errore durante il salvataggio dell'item nella wishlist: " + e.getMessage());
            throw e;
        } finally {
            // Chiudiamo le risorse
            closeResources(preparedStatement, connection);
            System.out.println("Risorse chiuse.");
        }

        // Log: Verifica esito
        if (result != 0) {
            System.out.println("Salvataggio completato con successo.");
        } else {
            System.out.println("Salvataggio non riuscito.");
        }

        return result != 0;
    }

    public synchronized boolean doDelete(int idItem){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE idItem = ?";

        try{
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);

            preparedStatement.setInt(1, idItem);

            result = preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
            logger.warning("Errore durante la rimozione di item wishlist dal database");
        } finally {
            closeResources(preparedStatement, connection);
        }

        return result != 0;
    }

    public synchronized List<ItemWishlistBean> doRetrieveByIdWishlist(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<ItemWishlistBean> list = new ArrayList<ItemWishlistBean>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE idWishlist = ?";

        try{
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
               ItemWishlistBean bean = new ItemWishlistBean();

               bean.setIdItem(rs.getInt("idItem"));
               bean.setIdWishlist(rs.getInt("idWishlist"));
               bean.setNomeProdotto(rs.getString("nomeProdotto"));

               list.add(bean);
            }

            rs.close();
        } catch(SQLException e){
            e.printStackTrace();
            logger.warning("Errore durante il fetch di item wishlist dal database");
        }finally{
            closeResources(connection, preparedStatement);
        }

        return list;
    }

    public synchronized ItemWishlistBean doRetrieveByIdItem(int idItem){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ItemWishlistBean bean = new ItemWishlistBean();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE idItem = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idItem);
            ResultSet rs = preparedStatement.executeQuery();

            bean.setIdItem(rs.getInt("idItem"));
            bean.setIdWishlist(rs.getInt("idWishlist"));
            bean.setNomeProdotto(rs.getString("nomeProdotto"));

            rs.close();

        } catch (SQLException e){
           e.printStackTrace();
           logger.warning("Errore durante il fetch dell'itemwishlist dal database");
        }finally {
            closeResources(preparedStatement, connection);
        }
        return bean;

    }

    public synchronized boolean doDeleteByNomeProdottoEIdWishlist(String nomeProdotto, int idWishlist) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE nomeProdotto = ? AND idWishlist = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);

            preparedStatement.setString(1, nomeProdotto);
            preparedStatement.setInt(2, idWishlist);

            result = preparedStatement.executeUpdate();

            if (result > 0) {
                logger.info("Elemento eliminato con successo dalla wishlist.");
            } else {
                logger.warning("Nessun elemento trovato con il nome prodotto: " + nomeProdotto + " e idWishlist: " + idWishlist);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.warning("Errore durante la rimozione dell'item dalla wishlist: " + e.getMessage());
        } finally {
            closeResources(preparedStatement, connection);
        }

        return result != 0;
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
