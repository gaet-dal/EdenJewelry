package test.dataManagement.dao;

import main.java.dataManagement.bean.ItemWishlistBean;
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

import main.java.dataManagement.dao.ItemWishlistDAO;

public class ItemWishlistDAOTest {

    @Mock
    private DataSource ds;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private ItemWishlistDAO itemWishlistDAO;

    @Before
    public void setUp() throws SQLException {
        System.out.println("Inizializzazione dei mock");
        MockitoAnnotations.openMocks(this);
        itemWishlistDAO = new ItemWishlistDAO(ds);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Chiusura delle risorse mock");
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void testDoSave() throws SQLException {
        ItemWishlistBean item = new ItemWishlistBean();
        item.setIdWishlist(1);
        item.setNomeProdotto("Prodotto di test");

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = itemWishlistDAO.doSave(item);
        assertTrue(result);

        verify(preparedStatement).setInt(1, item.getIdWishlist());
        verify(preparedStatement).setString(2, item.getNomeProdotto());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testDoDelete() throws SQLException {
        int idItem = 1;

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = itemWishlistDAO.doDelete(idItem);
        assertTrue(result);

        verify(preparedStatement).setInt(1, idItem);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testDoRetrieveByIdWishlist() throws SQLException {
        int idWishlist = 1;

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("idItem")).thenReturn(1);
        when(resultSet.getInt("idWishlist")).thenReturn(idWishlist);
        when(resultSet.getString("nomeProdotto")).thenReturn("Prodotto di test");

        List<ItemWishlistBean> result = itemWishlistDAO.doRetrieveByIdWishlist(idWishlist);
        assertEquals(1, result.size());
        assertEquals(idWishlist, result.get(0).getIdWishlist());
        assertEquals("Prodotto di test", result.get(0).getNomeProdotto());

        verify(preparedStatement).setInt(1, idWishlist);
        verify(preparedStatement).executeQuery();
        verify(resultSet).close();
    }

    @Test
    public void testDoRetrieveByIdItem() throws SQLException {
        int idItem = 1;

        when(ds.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("idItem")).thenReturn(idItem);
        when(resultSet.getInt("idWishlist")).thenReturn(1);
        when(resultSet.getString("nomeProdotto")).thenReturn("Prodotto di test");

        ItemWishlistBean result = itemWishlistDAO.doRetrieveByIdItem(idItem);
        assertNotNull(result);
        assertEquals(idItem, result.getIdItem());
        assertEquals(1, result.getIdWishlist());
        assertEquals("Prodotto di test", result.getNomeProdotto());

        verify(preparedStatement).setInt(1, idItem);
        verify(preparedStatement).executeQuery();
        verify(resultSet).close();
    }
}
