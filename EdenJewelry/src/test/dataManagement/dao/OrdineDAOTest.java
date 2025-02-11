package test.dataManagement.dao;

import main.java.dataManagement.bean.OrdineBean;
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
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import main.java.dataManagement.dao.OrdineDAO;

public class OrdineDAOTest {
    @Mock
    private DataSource ds;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private OrdineDAO ordineDAO;

    @Before
    public void setUp() throws SQLException {
        System.out.println("Inizializzazione dei mock");
        MockitoAnnotations.openMocks(this);
        ordineDAO = new OrdineDAO(ds);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Chiusura delle risorse mock");
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void testDoSave() throws SQLException {
        OrdineBean bean = new OrdineBean();
        bean.setIdOrdine(1);
        bean.setEmail("test@gmail.com");
        bean.setIndirizzo("via test 1");
        bean.setMetodoPagamento("paypal");
        bean.setTotale(50.5f);

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = ordineDAO.doSave(bean);
        assertTrue(result);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setInt(1, bean.getIdOrdine());
        verify(preparedStatement).setString(2, bean.getEmail());
        verify(preparedStatement).setFloat(3, bean.getTotale());
        verify(preparedStatement).setString(4, bean.getMetodoPagamento());
        verify(preparedStatement).setString(5, bean.getIndirizzo());
    }

    @Test
    public void testDoDelete() throws SQLException {
        String email = "test@gmail.com";

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = ordineDAO.doDelete(email);
        assertTrue(result);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setString(1, email);
    }

    @Test
    public void testDoRetrieveAll() throws SQLException {
        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("numeroOrdine")).thenReturn(1);
        when(resultSet.getString("email")).thenReturn("test@gmail.com");
        when(resultSet.getFloat("totale")).thenReturn(50.5f);
        when(resultSet.getString("metodo di pagamento")).thenReturn("paypal");
        when(resultSet.getString("indirizzo")).thenReturn("via test 1");

        List<OrdineBean> list = ordineDAO.doRetrieveAll();
        assertEquals(1, list.size());
        assertEquals("test@gmail.com", list.get(0).getEmail());
        assertEquals(50.5f, list.get(0).getTotale(), 0.01);
        assertEquals(1, list.get(0).getIdOrdine());
        assertEquals("paypal", list.get(0).getMetodoPagamento());
        assertEquals("via test 1", list.get(0).getIndirizzo());

        verify(preparedStatement).executeQuery();
        verify(resultSet).close();
    }

    @Test
    public void testDoRetrieveByNumeroOrdine() throws SQLException {
        int numeroOrdine = 1;

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("email")).thenReturn("test@gmail.com");
        when(resultSet.getFloat("totale")).thenReturn(50.5f);
        when(resultSet.getString("metodo di pagamento")).thenReturn("paypal");
        when(resultSet.getString("indirizzo")).thenReturn("via test 1");

        OrdineBean result = ordineDAO.doRetrieveByNumeroOrdine(numeroOrdine);
        assertNotNull(result);
        assertEquals("test@gmail.com", result.getEmail());
        assertEquals(50.5f, result.getTotale(), 0.01);
        assertEquals("paypal", result.getMetodoPagamento());
        assertEquals("via test 1", result.getIndirizzo());

        verify(resultSet).close();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setInt(1, numeroOrdine);
    }

    @Test
    public void testDoRetrieveUltimoOrdine() throws SQLException {
        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("numeroOrdine")).thenReturn(1);
        when(resultSet.getString("email")).thenReturn("test@gmail.com");
        when(resultSet.getFloat("totale")).thenReturn(50.5f);
        when(resultSet.getString("metodo di pagamento")).thenReturn("paypal");
        when(resultSet.getString("indirizzo")).thenReturn("via test 1");

        OrdineBean result = ordineDAO.doRetrieveUltimoOrdine();
        assertNotNull(result);
        assertEquals("test@gmail.com", result.getEmail());
        assertEquals(50.5f, result.getTotale(), 0.01);
        assertEquals("paypal", result.getMetodoPagamento());
        assertEquals("via test 1", result.getIndirizzo());

        verify(resultSet).close();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }
}