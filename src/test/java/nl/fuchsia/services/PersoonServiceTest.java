package nl.fuchsia.services;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.repository.PersoonRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PersoonServiceTest {

    @Mock
    PersoonRepository persoonRepository;
    @InjectMocks
    PersoonService persoonService;
    private List<Persoon> personenLijst = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        personenLijst.add(new Persoon(1, "Rense", "Houwing", "Voltawerk", "36", "8401 EN", "Gorredijk", "123456789", LocalDate.of(1967, 10, 12)));
    }

    @Test
    public void testGetPersonen() {
        /**
         * Test de methode getPersonen
         */
        when(persoonService.getPersonen())
                .thenReturn(Collections.emptyList());
        /**
         * Test de mock persoonRepository
         */
        when(persoonRepository.getAllePersonen())
                .thenReturn(personenLijst);
    }

    @Test
    public void testAddPersoon() {
        /**
         * Test de methode addPersoonService
         */
        Persoon persoon = new Persoon();
        persoonService.addPersoonService(persoon);
        verify(persoonRepository).addPersoonById(persoon);
    }
}