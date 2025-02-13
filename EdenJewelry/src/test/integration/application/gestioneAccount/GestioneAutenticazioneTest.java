package test.integration.application.gestioneAccount;

import main.java.application.gestioneAccount.GestioneAutenticazione;
import main.java.dataManagement.bean.UtenteBean;
import main.java.dataManagement.dao.UtenteDAO;
import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpSession;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GestioneAutenticazioneTest {
    @Mock
    private UtenteDAO utenteDAO;

    @Mock
    HttpSession session;

    private GestioneAutenticazione gestioneAutenticazione;


    @Before
    public void setUp() {
        System.out.println("Inizializzazione dei mock");
        MockitoAnnotations.openMocks(this);
        gestioneAutenticazione = new GestioneAutenticazione();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Chiusura delle risorse mock");
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void testLoginSuccess() throws SQLException {
        String email = "test@example.com";
        String hashing = "password";

        UtenteBean expectedUser = new UtenteBean();
        expectedUser.setPassword(hashing);
        expectedUser.setEmail(email);

        when(utenteDAO.doRetrieveByEmail(email)).thenReturn(expectedUser);

        UtenteBean result = gestioneAutenticazione.login(email, hashing, utenteDAO);

        assertNotNull(result);
        assertEquals(expectedUser, result);

        verify(utenteDAO).doRetrieveByEmail(email);
    }

    @Test
    public void testLoginPwdErrata() throws SQLException {
        String email = "test@example.com";
        String hashing = "password";

        UtenteBean expectedUser = new UtenteBean();
        expectedUser.setPassword(hashing);
        expectedUser.setEmail(email);

        when(utenteDAO.doRetrieveByEmail(email)).thenReturn(expectedUser);

        UtenteBean result = gestioneAutenticazione.login(email, "passwordErrata", utenteDAO);

        assertNull(result);
        assertNotEquals(expectedUser, result);

        verify(utenteDAO).doRetrieveByEmail(email);
    }

    @Test
    public void testLoginEmailInesistente() throws SQLException {
        String email = "test@example.com";

        UtenteBean user = new UtenteBean();
        user.setEmail(email);

        when(utenteDAO.doRetrieveByEmail(email)).thenReturn(null);

        UtenteBean result = gestioneAutenticazione.login(email, "password", utenteDAO);

        assertNull(result);

        verify(utenteDAO).doRetrieveByEmail(email);
    }

    @Test
    public void testLogoutLogged() {
        boolean result = gestioneAutenticazione.logout(session);

        assertTrue(result);
        verify(session).invalidate();
    }

    @Test
    public void testLogoutNotLogged() {
        boolean result = gestioneAutenticazione.logout(null);

        assertFalse(result);
    }
}