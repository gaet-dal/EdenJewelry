package test.unit.dataManagement.dao;

import main.java.dataManagement.bean.OrdineBean;
import main.java.dataManagement.bean.UtenteBean;
import main.java.dataManagement.dao.UtenteDAO;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class UtenteDAOTest {
    @Mock
    private DataSource ds;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private UtenteDAO utenteDAO;

    @Before
    public void setUp() {
        System.out.println("Inizializzazione dei mock");
        MockitoAnnotations.openMocks(this);
        utenteDAO = new UtenteDAO(ds);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Chiusura delle risorse mock");
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void testDoSave() throws SQLException {
        UtenteBean bean = new UtenteBean();
        bean.setNome("nome");
        bean.setCognome("cognome");
        bean.setEmail("test@gmail.com");
        bean.setPassword("password");
        bean.setTipo("utente");

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = utenteDAO.doSave(bean);
        assertTrue(result);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setString(1, bean.getEmail());
        verify(preparedStatement).setString(2, bean.getNome());
        verify(preparedStatement).setString(3, bean.getCognome());
        verify(preparedStatement).setString(4, bean.getPassword());
        verify(preparedStatement).setString(5, bean.getTipo());
        verify(preparedStatement).close();
    }

    @Test
    public void testDoDelete() throws SQLException{
        String email="test@gmail.com";

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = utenteDAO.doDelete(email);
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
        when(resultSet.getString("nome")).thenReturn("nome");
        when(resultSet.getString("cognome")).thenReturn("cognome");
        when(resultSet.getString("email")).thenReturn("test@gmail.com");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("tipo")).thenReturn("user");

        List<UtenteBean> list = utenteDAO.doRetrieveAll();
        assertEquals(1, list.size());
        assertEquals("nome", list.get(0).getNome());
        assertEquals("cognome", list.get(0).getCognome());
        assertEquals("test@gmail.com", list.get(0).getEmail());
        assertEquals("password", list.get(0).getPassword());
        assertEquals("user", list.get(0).getTipo());

        verify(preparedStatement).executeQuery();
        verify(resultSet).close();
    }

    @Test
    public void testDoRetrieveByEmail() throws SQLException {
        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("nome")).thenReturn("nome");
        when(resultSet.getString("cognome")).thenReturn("cognome");
        when(resultSet.getString("email")).thenReturn("test@gmail.com");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("tipo")).thenReturn("utente");

        UtenteBean result = utenteDAO.doRetrieveByEmail("email");
        assertEquals("nome", result.getNome());
        assertEquals("cognome", result.getCognome());
        assertEquals("test@gmail.com", result.getEmail());
        assertEquals("password", result.getPassword());
        assertEquals("utente", result.getTipo());

        verify(resultSet).close();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setString(1, "email");
    }
}