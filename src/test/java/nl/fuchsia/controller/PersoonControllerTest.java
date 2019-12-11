package nl.fuchsia.controller;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.services.PersoonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class PersoonControllerTest {

    @Mock
    PersoonService persoonService;
    @InjectMocks
    PersoonController persoonController;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    /**
     * Test of de methode getPersonen in de persoonService wordt aangeroepen.
     */
    @Test
    public void testGetPersonen() {
        persoonController.getOrmPersonen();
        verify(persoonService).getOrmPersonen();
    }

    /**
     * Test of de methode addPersoon in de persoonService wordt aangeroepen.
     */
    @Test
    public void testAddPersoon() {
        Persoon persoon = new Persoon();
        persoonController.addPersoon(persoon);
        verify(persoonService).addPersoon(persoon);
    }

    @Test
    public void testGetJdbcPersonen(){
        persoonController.getJdbcPersonen();
        verify(persoonService).getJdbcPersonen();
    }
}