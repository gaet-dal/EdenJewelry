package test.dataManagement.dao;

import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.dao.ProdottoDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProdottoDAOTest {
    @Mock
    private DataSource ds;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private ProdottoDAO prodottoDAO;

    @Before
    public void setUp() throws SQLException {
        System.out.println("Inizializzazione dei mock");
        MockitoAnnotations.openMocks(this);
        prodottoDAO = new ProdottoDAO(ds);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Chiusura delle risorse mock");
        MockitoAnnotations.openMocks(this).close();
    }


    @Test
    public void testDoSave() throws SQLException {
        ProdottoBean bean = new ProdottoBean();
        bean.setNome("Collana argento");
        bean.setPrezzo(25.5f);
        bean.setQuantita(10);
        bean.setCategoria("collane");
        bean.setImmagine("collana.jpg");

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = prodottoDAO.doSave(bean);
        assertTrue(result);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setString(1, bean.getNome());
        verify(preparedStatement).setFloat(2, bean.getPrezzo());
        verify(preparedStatement).setInt(3, bean.getQuantita());
        verify(preparedStatement).setString(4, bean.getCategoria());
        verify(preparedStatement).setString(5, bean.getImmagine());
    }

    @Test
    public void testDoDelete() throws SQLException {
        String nome = "Collana argento";

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = prodottoDAO.doDelete(nome);
        assertTrue(result);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setString(1, nome);
    }

    @Test
    public void testDoRetrieveAll() {
    }

    @Test
    public void testDoRetrieveByNome() {
    }

    @Test
    public void testIsEmpty() {
    }

    @Test
    public void testCercaProdottiPerNome() {
    }
}