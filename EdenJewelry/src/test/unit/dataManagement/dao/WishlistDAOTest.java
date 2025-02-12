package test.unit.dataManagement.dao;

import main.java.dataManagement.bean.UtenteBean;
import main.java.dataManagement.bean.WishlistBean;
import main.java.dataManagement.bean.WishlistBean;
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

import main.java.dataManagement.dao.WishlistDAO;


public class WishlistDAOTest {
    @Mock
    private DataSource ds;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private WishlistDAO wishlistDAO;

    @Before
    public void setUp() throws SQLException {
        System.out.println("Inizializzazione dei mock");
        MockitoAnnotations.openMocks(this);
        wishlistDAO = new WishlistDAO(ds);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Chiusura delle risorse mock");
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void testDoSave() throws SQLException {
        WishlistBean bean = new WishlistBean();
        bean.setIdWishlist(1);
        bean.setEmail("test@gmail.com");

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = wishlistDAO.doSave(bean);
        assertTrue(result);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setString(1, bean.getEmail());
    }

    @Test
    public void testDoDelete() throws SQLException {
        String email = "test@gmail.com";

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = wishlistDAO.doDelete(email);
        assertTrue(result);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).setString(1, email);
    }

    public void testDoRetrieveAll() throws SQLException {
        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString("email")).thenReturn("test@gmail.com");


        List<WishlistBean> list = wishlistDAO.doRetrieveAll();
        assertEquals("1", list.get(0).getIdWishlist());
        assertEquals("test@gmail.com", list.get(0).getEmail());


        verify(preparedStatement).executeQuery();
        verify(resultSet).close();
    }

    @Test
    public void testDoRetrieveByEmail() throws SQLException {
        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString("email")).thenReturn("test@gmail.com");

        WishlistBean result = wishlistDAO.doRetrieveByEmail("email");
        assertEquals("test@gmail.com", result.getEmail());

        verify(resultSet).close();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setString(1, "email");
    }
}
