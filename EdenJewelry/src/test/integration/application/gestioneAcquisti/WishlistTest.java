package test.integration.application.gestioneAcquisti;

import main.java.application.gestioneAcquisti.Wishlist;
import main.java.dataManagement.bean.ItemWishlistBean;
import main.java.dataManagement.bean.WishlistBean;
import main.java.dataManagement.dao.ItemWishlistDAO;
import main.java.dataManagement.dao.WishlistDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class WishlistTest {

    @InjectMocks
    private Wishlist wishlist;

    @Mock
    private DataSource ds; // Mock del DataSource

    @Mock
    private WishlistDAO wishlistDAO;

    @Mock
    private ItemWishlistDAO itemWishlistDAO;

    @Mock
    private WishlistBean wishlistMock;

    @Mock
    private ItemWishlistBean itemMock;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAggiungiWishlistUtenteLoggato() throws SQLException {
        String nomeProdotto = "Anello";
        String emailUtente = "utente@email.com";

        // Configura il comportamento del mock
        when(wishlistMock.getIdWishlist()).thenReturn(1);
        when(wishlistDAO.doRetrieveByEmail(emailUtente)).thenReturn(wishlistMock);

        when(itemWishlistDAO.doSave(any(ItemWishlistBean.class))).thenReturn(true);

        // Esegui il test
        boolean risultato = wishlist.aggiungiWishlist(nomeProdotto, emailUtente, ds);

        // Verifica il risultato
        assertTrue(risultato);

        // Verifica che il metodo doSave sia stato chiamato
        verify(itemWishlistDAO).doSave(any(ItemWishlistBean.class));
    }

    @Test
    public void testAggiungiWishlistUtenteNonLoggato() {
        boolean risultato = wishlist.aggiungiWishlist("Anello", "", ds);
        assertFalse(risultato);
    }

    @Test
    public void testRemoveWishlistProdottoEliminato() throws SQLException {
        when(itemWishlistDAO.doDelete(1)).thenReturn(true);
        boolean risultato = wishlist.removeWishlist(1, ds);
        assertTrue(risultato);
    }

    @Test
    public void testRemoveWishlistProdottoNonEliminato() throws SQLException {
        when(itemWishlistDAO.doDelete(1)).thenReturn(false);
        boolean risultato = wishlist.removeWishlist(1, ds);
        assertFalse(risultato);
    }

    @Test
    public void testViewWishList() throws SQLException {
        String emailUtente = "utente@email.com";
        when(wishlistDAO.doRetrieveByEmail(emailUtente)).thenReturn(wishlistMock);
        when(itemWishlistDAO.doRetrieveByIdWishlist(1)).thenReturn(Collections.singletonList(itemMock));
        when(itemMock.getNomeProdotto()).thenReturn("Anello");

        List<ItemWishlistBean> lista = wishlist.viewWishList(emailUtente, ds);

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("Anello", lista.get(0).getNomeProdotto());
    }
}