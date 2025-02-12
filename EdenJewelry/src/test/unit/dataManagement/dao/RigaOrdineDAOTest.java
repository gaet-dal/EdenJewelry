package test.unit.dataManagement.dao;

import main.java.dataManagement.bean.RigaOrdineBean;
import main.java.dataManagement.dao.RigaOrdineDAO;
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


public class RigaOrdineDAOTest {
    @Mock
    private DataSource ds;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private RigaOrdineDAO rigaOrdineDAO;

    @Before
    public void setUp() {
        System.out.println("Inizializzazione dei mock");
        MockitoAnnotations.openMocks(this);
        rigaOrdineDAO = new RigaOrdineDAO(ds);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Chiusura delle risorse mock");
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void testDoSave() throws SQLException {
        RigaOrdineBean riga = new RigaOrdineBean();
        riga.setNomeProdotto("Collana argento");
        riga.setNumeroOrdine(1);
        riga.setPrezzoUnitario(20.5f);
        riga.setQuantità(2);

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = rigaOrdineDAO.doSave(riga);
        assertTrue(result);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setString(1, riga.getNomeProdotto());
        verify(preparedStatement).setInt(2, riga.getNumeroOrdine());
        verify(preparedStatement).setInt(3, riga.getQuantità());
        verify(preparedStatement).setFloat(4, riga.getPrezzoUnitario());
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    public void testDoDelete() throws SQLException {
        int numeroOrdine = 1;

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = rigaOrdineDAO.doDelete(numeroOrdine);
        assertTrue(result);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setInt(1, numeroOrdine);
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    public void testDoRetrieveByNumeroOrdine() throws SQLException {
        int numeroOrdine = 1;

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("idRiga")).thenReturn(1);
        when(resultSet.getString("nomeProdotto")).thenReturn("Collana argento");
        when(resultSet.getInt("numeroOrdine")).thenReturn(numeroOrdine);
        when(resultSet.getInt("quantità")).thenReturn(2);
        when(resultSet.getFloat("prezzo")).thenReturn(20.5f);

        List<RigaOrdineBean> list = rigaOrdineDAO.doRetrieveByNumeroOrdine(numeroOrdine);
        assertTrue(!list.isEmpty());
        assertEquals(1, list.get(0).getId());
        assertEquals("Collana argento", list.get(0).getNomeProdotto());
        assertEquals(numeroOrdine, list.get(0).getNumeroOrdine());
        assertEquals(2, list.get(0).getQuantità());
        assertEquals(20.5f, list.get(0).getPrezzoUnitario(), 0.01);

        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setInt(1, numeroOrdine);
        verify(resultSet).close();
        verify(preparedStatement).close();
        verify(connection).close();

    }

    @Test
    public void testDoRetrieveByIdRiga() throws SQLException {
        int idRiga = 1;

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("idRiga")).thenReturn(idRiga);
        when(resultSet.getString("nomeProdotto")).thenReturn("Collana argento");
        when(resultSet.getInt("numeroOrdine")).thenReturn(1);
        when(resultSet.getInt("quantità")).thenReturn(2);
        when(resultSet.getFloat("prezzo")).thenReturn(20.5f);

        RigaOrdineBean result = rigaOrdineDAO.doRetrieveByIdRiga(idRiga);
        assertNotNull(result);
        assertEquals("Collana argento", result.getNomeProdotto());
        assertEquals(1, result.getNumeroOrdine());
        assertEquals(idRiga, result.getId());
        assertEquals(2, result.getQuantità());
        assertEquals(20.5f, result.getPrezzoUnitario(), 0.01);

        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setInt(1, idRiga);
        verify(resultSet).close();
        verify(preparedStatement).close();
        verify(connection).close();
    }
}