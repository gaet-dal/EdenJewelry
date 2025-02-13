package test.unit.dataManagement.dao;

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
import java.util.List;

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
    public void testDoRetrieveAll() throws SQLException {
        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("nome")).thenReturn("Collana argento");
        when(resultSet.getFloat("prezzo")).thenReturn(25.5f);
        when(resultSet.getInt("quantità")).thenReturn(10);
        when(resultSet.getString("categoria")).thenReturn("collane");
        when(resultSet.getString("immagine")).thenReturn("collana.jpg");

        List<ProdottoBean> list = prodottoDAO.doRetrieveAll();
        assertEquals(1, list.size());
        assertEquals("Collana argento", list.get(0).getNome());
        assertEquals(25.5f, list.get(0).getPrezzo(), 0.01);
        assertEquals(10, list.get(0).getQuantita());
        assertEquals("collane", list.get(0).getCategoria());
        assertEquals("collana.jpg", list.get(0).getImmagine());

        verify(preparedStatement).executeQuery();
        verify(resultSet).close();
    }

    @Test
    public void testDoRetrieveByNome() throws SQLException {
        String nome = "Collana argento";

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);

        when(resultSet.getString("nome")).thenReturn("Collana argento");
        when(resultSet.getFloat("prezzo")).thenReturn(25.5f);
        when(resultSet.getInt("quantità")).thenReturn(10);
        when(resultSet.getString("categoria")).thenReturn("collane");
        when(resultSet.getString("immagine")).thenReturn("collana.jpg");

        ProdottoBean bean = prodottoDAO.doRetrieveByNome(nome);
        assertNotNull(bean);
        assertEquals("Collana argento", bean.getNome());
        assertEquals(25.5f, bean.getPrezzo(), 0.01);
        assertEquals(10, bean.getQuantita());
        assertEquals("collane", bean.getCategoria());
        assertEquals("collana.jpg", bean.getImmagine());

        verify(preparedStatement).executeQuery();
        verify(resultSet).close();
        verify(preparedStatement).setString(1, nome);
    }

    @Test
    public void testIsEmpty() throws SQLException {
        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("total")).thenReturn(2);

        boolean result = prodottoDAO.isEmpty();
        assertFalse(result);

        verify(preparedStatement).executeQuery();
        verify(resultSet).close();
    }

    @Test
    public void testCercaProdottiPerNome() throws SQLException {
        String nome = "Collana argento";

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("nome")).thenReturn("Collana argento");
        when(resultSet.getFloat("prezzo")).thenReturn(25.5f);
        when(resultSet.getInt("quantità")).thenReturn(10);
        when(resultSet.getString("categoria")).thenReturn("collane");
        when(resultSet.getString("immagine")).thenReturn("collana.jpg");

        List<ProdottoBean> list = prodottoDAO.cercaProdottiPerNome(nome);
        assertEquals(1, list.size());
        assertEquals("Collana argento", list.get(0).getNome());
        assertEquals(25.5f, list.get(0).getPrezzo(), 0.01);
        assertEquals(10, list.get(0).getQuantita());
        assertEquals("collane", list.get(0).getCategoria());
        assertEquals("collana.jpg", list.get(0).getImmagine());

        verify(preparedStatement).setString(1, "%" + nome + "%");
        verify(preparedStatement).executeQuery();
        verify(resultSet).close();
    }
}