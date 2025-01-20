package main.java.gestioneProdotti.wishlist;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class WishlistDAO {
    private static DataSource ds;

    Logger logger = Logger.getLogger(WishlistDAO.class.getName());

    private static final String TABLE_NAME = "UTENTE";

    public WishlistDAO(DataSource ds) {
        this.ds = ds;

        if(ds == null) {
            logger.info("DataSource wishlist nullo");
        } else {
            logger.info("DataSource wishlist istanziato correttamente");
        }
    }

    public boolean doSave(WishlistBean wishlist) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (email, nome, cognome, password, tipo) VALUES (?, ?, ?, ?, ?)";

        try{
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, utente.getEmail());
            preparedStatement.setString(2, utente.getNome());
            preparedStatement.setString(3, utente.getCognome());
            preparedStatement.setString(4, utente.getPassword());
            preparedStatement.setString(5, utente.getTipo());

            result = preparedStatement.executeUpdate();
        }  finally {
            closeResources(preparedStatement, connection);
        }

        return result != 0;
    }
}