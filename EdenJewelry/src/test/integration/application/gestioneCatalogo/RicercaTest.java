package test.integration.application.gestioneCatalogo;

import main.java.application.gestioneCatalogo.Ricerca;
import main.java.dataManagement.bean.ProdottoBean;
import main.java.dataManagement.dao.ProdottoDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RicercaTest {
    @Mock
    ProdottoDAO prodottoDAO;

    Ricerca ricerca;

    @BeforeEach
    public void setUp() {
        System.out.println("Inizializzazione dei mock");
        MockitoAnnotations.openMocks(this);
        ricerca = new Ricerca();
    }

    @AfterEach
    public void tearDown() throws Exception {
        System.out.println("Chiusura delle risorse mock");
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void searchSuccess() throws SQLException {
        when(prodottoDAO.doRetrieveAll()).thenReturn(getMockItems());

        List<ProdottoBean> list = ricerca.search("collana", "collane", prodottoDAO);
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals("collana", list.get(0).getNome());

        verify(prodottoDAO).doRetrieveAll();
    }

    @Test
    public void searchNotSucces() throws SQLException {
        when(prodottoDAO.doRetrieveAll()).thenReturn(getMockItems());

        List<ProdottoBean> list = ricerca.search("notcollana", "collane", prodottoDAO);
        assertNotNull(list);
        assertTrue(list.isEmpty());

        verify(prodottoDAO).doRetrieveAll();
    }

    @Test
    public void searchWithEmptyName() throws SQLException {
        when(prodottoDAO.doRetrieveAll()).thenReturn(getMockItems());

        List<ProdottoBean> list = ricerca.search("", "collane", prodottoDAO);
        assertNotNull(list);
        assertTrue(list.size() > 0); // Dovrebbe restituire tutti gli elementi della categoria "collane"

        verify(prodottoDAO, times(1)).doRetrieveAll();
    }

    @Test
    public void searchWithNullCategory() throws SQLException {
        when(prodottoDAO.doRetrieveAll()).thenReturn(getMockItems());

        List<ProdottoBean> list = ricerca.search("collana", null, prodottoDAO);
        assertNotNull(list);
        assertTrue(list.size() > 0); // Dovrebbe restituire tutti gli elementi con nome "collana"

        verify(prodottoDAO, times(1)).doRetrieveAll();
    }

    @Test
    public void searchWithEmptyCategory() throws SQLException {
        when(prodottoDAO.doRetrieveAll()).thenReturn(getMockItems());

        List<ProdottoBean> list = ricerca.search("collana", "", prodottoDAO);
        assertNotNull(list);
        assertTrue(list.size() > 0); // Dovrebbe restituire tutti gli elementi con nome "collana"

        verify(prodottoDAO, times(1)).doRetrieveAll();
    }


    private List<ProdottoBean> getMockItems(){
        List<ProdottoBean> list = new ArrayList<>();

        ProdottoBean p1 = new ProdottoBean();
        ProdottoBean p2 = new ProdottoBean();

        p1.setNome("collana");
        p1.setCategoria("collane");
        p2.setNome("bracciale");
        p2.setCategoria("bracciali");

        list.add(p1);
        list.add(p2);

        return list;
    }
}