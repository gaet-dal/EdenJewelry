package test.unit.application.gestioneAccount;

import main.java.application.gestioneAccount.GestioneRegistrazione;
import main.java.dataManagement.bean.UtenteBean;
import main.java.dataManagement.dao.UtenteDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GestioneRegistrazioneTest {
    @Mock
    private UtenteDAO utenteDAO;

    @Mock
    private UtenteBean utente;

    private GestioneRegistrazione gestioneRegistrazione;

    @Before
    public void setUp() {
        System.out.println("Inizializzazione dei mock");
        MockitoAnnotations.openMocks(this);
        gestioneRegistrazione = new GestioneRegistrazione(utenteDAO);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Chiusura delle risorse mock");
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void registerSuccess() throws SQLException {
        when(utenteDAO.doSave(any(UtenteBean.class))).thenReturn(true);

        boolean result = gestioneRegistrazione.register("Mario", "Rossi", "test@gmail.com", "password", "user");
        assertTrue(result);

        verify(utenteDAO, times(1)).doSave(any(UtenteBean.class));
    }

    @Test
    public void registerInvalidEmail() throws SQLException {
        boolean result = gestioneRegistrazione.register("Mario", "Rossi", "test.gmail.com", "password", "user");
        assertFalse(result);
    }

    @Test
    public void registerDuplicate() throws SQLException {
        when(gestioneRegistrazione.checkEmail("test@gmail.com")).thenReturn(false);

        boolean result = gestioneRegistrazione.register("Mario", "Rossi", "test@gmail.com", "password", "user");
        assertFalse(result);
    }

    @Test
    public void registerInvalidLastName() throws SQLException {
        boolean result = gestioneRegistrazione.register("Mario", "R0ssi", "test@gmail.com", "password", "user");
        assertFalse(result);
    }

    @Test
    public void registerInvalidName() throws SQLException {
        boolean result = gestioneRegistrazione.register("Mari0", "Rossi", "test@gmail.com", "password", "user");
        assertFalse(result);
    }

}