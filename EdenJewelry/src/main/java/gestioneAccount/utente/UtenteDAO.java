package main.java.gestioneAccount.utente;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;

public class UtenteDAO {
    private static DataSource ds;

    Logger logger = Logger.getLogger(UtenteDAO.class.getName());

    private static final String TABLE_NAME = "UTENTE";

    public UtenteDAO(DataSource ds) {
        this.ds = ds;

        if(ds == null) {
            logger.info("DataSource utente nullo");
        } else {
            logger.info("DataSource utente istanziato correttamente");
        }
    }

    public synchronized boolean doSave(UtenteBean utente) throws SQLException{
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

    public synchronized boolean doDelete(String email){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE email = ?";

        try{
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);

            preparedStatement.setString(1, email);

            result = preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            closeResources(preparedStatement, connection);
        }

        return result != 0;
    }

    public synchronized List<UtenteBean> doRetrieveAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<UtenteBean> utenti = new ArrayList<UtenteBean>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + "ORDER BY email ASC";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                UtenteBean bean = new UtenteBean();

                bean.setNome(rs.getString("nome"));
                bean.setEmail(rs.getString("email"));
                bean.setCognome(rs.getString("cognome"));
                bean.setPassword(rs.getString("password"));
                bean.setTipo(rs.getString("tipo"));

                utenti.add(bean);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(preparedStatement, connection);
        }
        return utenti;
    }

    public synchronized UtenteBean doRetrieveByEmail(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        UtenteBean bean = new UtenteBean();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            bean.setNome(rs.getString("nome"));
            bean.setEmail(rs.getString("email"));
            bean.setCognome(rs.getString("cognome"));
            bean.setPassword(rs.getString("password"));
            bean.setTipo(rs.getString("tipo"));

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