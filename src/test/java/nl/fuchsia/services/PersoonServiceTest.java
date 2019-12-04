package nl.fuchsia.services;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.repository.PersoonRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class PersoonServiceTest {

    @Mock
    PersoonRepository persoonRepository;
    @InjectMocks
    PersoonService persoonService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    /**
     * Test of de methode getPersonen in de persoonRepository wordt aangeroepen.
     */
    @Test
    public void testGetPersonen() {
        persoonService.getPersonen();
        verify(persoonRepository).getPersonen();
    }

    /**
     * Test of de methode addPersoon in de persoonRepository wordt aangeroepen.
     */
    @Test
    public void testAddPersoon() {
        Persoon persoon = new Persoon();
        persoonService.addPersoon(persoon);
        verify(persoonRepository).addPersoon(persoon);
    }
}