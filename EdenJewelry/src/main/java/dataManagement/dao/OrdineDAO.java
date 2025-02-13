package main.java.dataManagement.dao;

import main.java.dataManagement.bean.OrdineBean;
import main.java.dataManagement.bean.ProdottoBean;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OrdineDAO {

    private static DataSource ds;

    Logger logger = Logger.getLogger(UtenteDAO.class.getName());

    private static final String TABLE_NAME = "ORDINE";

    //verifichiamo se la connessione è avvennuta correttamente
    public OrdineDAO(DataSource ds) {
        this.ds = ds;

        if(ds == null) {
            logger.info("DataSource ordine nullo"); //connessione non creata;
        } else {
            logger.info("DataSource ordine istanziato correttamente"); //connessione creata correttamente
        }
    }


    public synchronized boolean doSave(OrdineBean ordine) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String insertSQL = "INSERT INTO " + TABLE_NAME + " (email, totale, metodoPagamento, indirizzo) VALUES (?, ?, ?, ?)";

        try{
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, ordine.getEmail());
            preparedStatement.setFloat(2, ordine.getTotale());
            preparedStatement.setString(3, ordine.getMetodoPagamento());
            preparedStatement.setString(4, ordine.getIndirizzo());

            System.out.println("📦 Tentativo di inserimento ordine:");
            System.out.println("Email: " + ordine.getEmail());
            System.out.println("Totale: " + ordine.getTotale());
            System.out.println("Metodo Pagamento: " + ordine.getMetodoPagamento());
            System.out.println("Indirizzo: " + ordine.getIndirizzo());

            try {
                result = preparedStatement.executeUpdate();
                System.out.println("✅ Ordine salvato con successo.");
            } catch (SQLException e) {
                System.out.println("❌ Errore SQL durante il salvataggio dell'ordine:");
                e.printStackTrace();
            }
            //result = preparedStatement.executeUpdate();
        }  finally {
            closeResources(preparedStatement, connection);
        }

        return result != 0;
    }

    public synchronized boolean doDelete(String email){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE numeroOrdine = ?";

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

    public synchronized List<OrdineBean> doRetrieveAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<OrdineBean> ordini = new ArrayList<OrdineBean>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " ORDER BY email ASC";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                OrdineBean bean = new OrdineBean();

                bean.setIdOrdine(rs.getInt("numeroOrdine"));
                bean.setEmail(rs.getString("email"));
                bean.setTotale(rs.getFloat("totale"));
                bean.setMetodoPagamento(rs.getString("metodoPagamento"));
                bean.setIndirizzo(rs.getString("indirizzo"));

                ordini.add(bean);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(preparedStatement, connection);
        }
        return ordini;
    }


    public synchronized OrdineBean doRetrieveByNumeroOrdine(int numeroOrdine) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        OrdineBean bean = new OrdineBean();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE idOrdine = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, numeroOrdine);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) { // AGGIUNTO rs.next()
                bean.setEmail(rs.getString("email"));
                bean.setTotale(rs.getFloat("totale"));
                bean.setMetodoPagamento(rs.getString("metodoPagamento"));
                bean.setIndirizzo(rs.getString("indirizzo"));
            }

            rs.close();

        } finally {
            closeResources(preparedStatement, connection);
        }
        return bean;
    }


    public synchronized OrdineBean doRetrieveUltimoOrdine() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        OrdineBean bean = new OrdineBean();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " ORDER BY numeroOrdine DESC";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {  // AGGIUNTO rs.next()
                bean.setIdOrdine(rs.getInt("numeroOrdine"));
                bean.setEmail(rs.getString("email"));
                bean.setTotale(rs.getFloat("totale"));
                bean.setMetodoPagamento(rs.getString("metodoPagamento"));
                bean.setIndirizzo(rs.getString("indirizzo"));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(preparedStatement, connection);
        }

       if(bean==null) {
           return null; //se non è stato effettuato nessun ordine, torna null;
       }
       else return bean;
    }

    public synchronized List<OrdineBean> doRetrieveByMail(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<OrdineBean> ordini = new ArrayList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ? ORDER BY numeroOrdine DESC";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                OrdineBean bean = new OrdineBean();
                bean.setIdOrdine(rs.getInt("numeroOrdine"));
                bean.setEmail(rs.getString("email"));
                bean.setTotale(rs.getFloat("totale"));
                bean.setMetodoPagamento(rs.getString("metodoPagamento"));
                bean.setIndirizzo(rs.getString("indirizzo"));

                ordini.add(bean);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(preparedStatement, connection);
        }
        return ordini;
    }

    public void closeResources(AutoCloseable... resources) {
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
