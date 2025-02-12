package test.unit.application.gestioneAcquisti;

import main.java.application.gestioneAcquisti.Wishlist;
import main.java.dataManagement.bean.WishlistBean;
import main.java.dataManagement.dao.WishlistDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class WishlistTest {
    private Wishlist wishlist;
    private DataSource ds;
    private WishlistDAO wishlistDAO;

    @Before
    public void setUp() {
        ds = mock(DataSource.class);
        wishlistDAO = mock(WishlistDAO.class);
        wishlist = new Wishlist();
    }

    @Test
    public void testAggiungiWishlistUtenteLoggato() throws SQLException {
        when(wishlistDAO.doRetrieveByEmail("utente@email.com"))
                .thenReturn(new WishlistBean());
        boolean risultato = wishlist.aggiungiWishlist("Anello", "utente@email.com", ds);
        assertTrue(risultato);
    }

    @Test
    public void testAggiungiWishlistUtenteNonLoggato() {
        boolean risultato = wishlist.aggiungiWishlist("Anello", "", ds);
        assertFalse(risultato);
    }

    @Test
    public void testRemoveWishlistProdottoEliminato() throws SQLException {
        when(wishlistDAO.doDelete(String.valueOf(1))).thenReturn(true);
        boolean risultato = wishlist.removeWishlist(1, ds);
        assertTrue(risultato);
    }

    @Test
    public void testRemoveWishlistProdottoNonEliminato() throws SQLException {
        when(wishlistDAO.doDelete(String.valueOf(1))).thenReturn(false);
        boolean risultato = wishlist.removeWishlist(1, ds);
        assertFalse(risultato);
    }
}
