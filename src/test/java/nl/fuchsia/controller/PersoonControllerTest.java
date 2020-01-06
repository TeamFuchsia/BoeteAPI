package nl.fuchsia.controller;

import nl.fuchsia.dto.PersoonEditDto;
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
        persoonController.getPersonen();

        verify(persoonService).getPersonen();
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

    /**
     * Test of de methode getPersoonById in de persoonService wordt aangeroepen.
     */
    @Test
    public void testGetPersoonById() {
        persoonController.getPersoonById(1);

        verify(persoonService).getPersoonById(1);
    }

    /**
     * Test of de methode updatePersoonById in de persoonService wordt aangeroepen.
     */
    @Test
    public void testUpdatePersoonById() {
        Persoon persoon = new Persoon();

        persoonController.updatePersoonById(persoon);

        verify(persoonService).updatePersoonById(persoon);
    }
}