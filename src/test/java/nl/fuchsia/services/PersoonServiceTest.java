package nl.fuchsia.services;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.repository.JdbcPersoonRepository;
import nl.fuchsia.repository.PersoonRepository;
;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class PersoonServiceTest {

    @Mock
    PersoonRepository persoonRepository;
    @Mock
    JdbcPersoonRepository jdbcPersoonRepository;
    @InjectMocks
    PersoonService persoonService;

    @BeforeEach
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
    public void testAddPersoon() throws Exception {
        Persoon persoon = new Persoon();
        persoonService.addPersoon(persoon);
        verify(persoonRepository).addPersoon(persoon);
    }

    @Test
    public void testGetJdbcPersonen(){
        persoonService.getJdbcPersonen();
        verify(jdbcPersoonRepository).getJdbcPersonen();
    }
}