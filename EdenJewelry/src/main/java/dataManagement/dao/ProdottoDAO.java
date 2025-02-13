package main.java.dataManagement.dao;

import main.java.dataManagement.bean.ProdottoBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;

public class ProdottoDAO {
    private static DataSource ds;

    Logger logger = Logger.getLogger(ProdottoDAO.class.getName());

    private static final String TABLE_NAME = "PRODOTTO";

    public ProdottoDAO(DataSource ds) {
        this.ds = ds;

        if(ds == null) {
            logger.info("DataSource prodotto nullo");
        } else {
            logger.info("DataSource prodotto istanziato correttamente");
        }
    }

    public synchronized boolean doSave(ProdottoBean prodotto) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (nome, prezzo, quantità, categoria, immagine) VALUES (?, ?, ?, ?, ?)";

        try{
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, prodotto.getNome());
            preparedStatement.setFloat(2, prodotto.getPrezzo());
            preparedStatement.setInt(3, prodotto.getQuantita());
            preparedStatement.setString(4, prodotto.getCategoria());
            preparedStatement.setString(5, prodotto.getImmagine());

            result = preparedStatement.executeUpdate();
        }  finally {
            closeResources(preparedStatement, connection);
        }

        return result != 0;
    }

    public synchronized boolean doDelete(String nome) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE nome = ?";

        try{
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);

            preparedStatement.setString(1, nome);

            result = preparedStatement.executeUpdate();
        } finally {
            closeResources(preparedStatement, connection);
        }

        return result != 0;
    }

    public synchronized List<ProdottoBean> doRetrieveAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<ProdottoBean> prodotti = new ArrayList<ProdottoBean>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " ORDER BY nome ASC";

        int count=0;
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProdottoBean bean = new ProdottoBean();

                bean.setNome(rs.getString("nome"));
                bean.setPrezzo(rs.getFloat("prezzo"));
                bean.setQuantita(rs.getInt("quantità"));
                bean.setCategoria(rs.getString("categoria"));
                bean.setImmagine(rs.getString("immagine"));

                prodotti.add(bean);
                count++;
            }
            rs.close();

        } finally {
            closeResources(preparedStatement, connection);
        }
        System.out.println("Totale prodotti trovati: " + count);
        return prodotti;
    }

    public synchronized ProdottoBean doRetrieveByNome(String nome) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ProdottoBean bean = new ProdottoBean();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE TRIM(LOWER(nome)) = TRIM(LOWER(?))";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, nome.trim()); // Elimina gli spazi dalla stringa ricevuta
            System.out.println("Eseguo query: " + preparedStatement.toString());

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                bean.setNome(rs.getString("nome").trim());  // Anche qui eliminiamo eventuali spazi
                bean.setPrezzo(rs.getFloat("prezzo"));
                bean.setQuantita(rs.getInt("quantità"));
                bean.setCategoria(rs.getString("categoria"));
                bean.setImmagine(rs.getString("immagine"));
            } else {
                System.out.println("Nessun prodotto trovato per il nome: " + nome);
                return null;
            }

            rs.close();
        } finally {
            closeResources(preparedStatement, connection);
        }
        return bean;
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

    public boolean isEmpty() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM prodotti";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt("total");
                return count == 0; // True se non ci sono prodotti, altrimenti False
            }
        }
        return true; // Valore predefinito se qualcosa va storto
    }

    //permette una ricerca parziale di una stringa
    public List<ProdottoBean> cercaProdottiPerNome(String x) throws SQLException {
        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE LOWER(nome) LIKE LOWER(?)"; // Converte in lowercase per ricerca case-insensitive

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + x + "%"); // Cerca qualsiasi parte del nome
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProdottoBean prodotto = new ProdottoBean();
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getFloat("prezzo"));
                prodotto.setQuantita(rs.getInt("quantità"));
                prodotto.setCategoria(rs.getString("categoria"));
                prodotto.setImmagine(rs.getString("immagine"));

                prodotti.add(prodotto);
            }
        }

        return prodotti;
    }

}