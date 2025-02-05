package main.java.dataManagement.dao;

import main.java.dataManagement.bean.RigaOrdineBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RigaOrdineDAO {
    private static DataSource ds;
    private static Logger logger = Logger.getLogger(RigaOrdineDAO.class.getName());
    private static final String TABLE_NAME = "RIGAORDINE";

    public RigaOrdineDAO(DataSource ds){
        this.ds = ds;

        if(ds == null) {
            logger.info("DataSource rigaordine nullo"); //connessione non creata;
        } else {
            logger.info("DataSource rigaordine istanziato correttamente"); //connessione creata correttamente
        }
    }

    public synchronized boolean doSave(RigaOrdineBean rigaOrdine){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String insertSQL = "INSERT INTO " + TABLE_NAME  +
                " (nomeProdotto, numeroOrdine, quantità, prezzo) VALUES (?, ?, ?, ?)";

        try{
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, rigaOrdine.getNomeProdotto());
            preparedStatement.setInt(2, rigaOrdine.getNumeroOrdine());
            preparedStatement.setInt(3, rigaOrdine.getQuantità());
            preparedStatement.setFloat(4, rigaOrdine.getPrezzoUnitario());

            result = preparedStatement.executeUpdate();
        }  catch (SQLException e){
            e.printStackTrace();
            logger.warning("Errore durante il salvataggio della rigaordine nel database");
        }finally {
            closeResources(preparedStatement, connection);
        }

        return result != 0;
    }

    public synchronized boolean doDelete(int numeroOrdine){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE numeroOrdine = ?";

        try{
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);

            preparedStatement.setInt(1, numeroOrdine);

            result = preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
            logger.warning("Errore durante la rimozione di rigaordine dal database");
        } finally {
            closeResources(preparedStatement, connection);
        }

        return result != 0;
    }

    public synchronized List<RigaOrdineBean> doRetrieveByNumeroOrdine(int numeroOrdine){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<RigaOrdineBean> list = new ArrayList<RigaOrdineBean>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE numeroOrdine = ?";

        try{
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                RigaOrdineBean bean = new RigaOrdineBean();

                bean.setId(rs.getInt("idRiga"));
                bean.setNomeProdotto(rs.getString("nomeProdotto"));
                bean.setNumeroOrdine(rs.getInt("numeroOrdine"));
                bean.setQuantità(rs.getInt("quantità"));
                bean.setPrezzoUnitario(rs.getFloat("prezzo"));

                list.add(bean);
            }

            rs.close();
        } catch(SQLException e){
            e.printStackTrace();
            logger.warning("Errore durante il fetch di rigaordine dal database");
        }finally{
            closeResources(connection, preparedStatement);
        }

        return list;
    }

    public synchronized RigaOrdineBean doRetrieveByIdRiga(int idRiga){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        RigaOrdineBean bean = new RigaOrdineBean();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE idRiga = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idRiga);
            ResultSet rs = preparedStatement.executeQuery();

            bean.setId(rs.getInt("idRiga"));
            bean.setNomeProdotto(rs.getString("nomeProdotto"));
            bean.setNumeroOrdine(rs.getInt("numeroOrdine"));
            bean.setQuantità(rs.getInt("quantità"));
            bean.setPrezzoUnitario(rs.getFloat("prezzo"));

            rs.close();

        } catch (SQLException e){
            e.printStackTrace();
            logger.warning("Errore durante il fetch della rigaordine dal database");
        }finally {
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
