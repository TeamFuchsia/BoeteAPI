package nl.fuchsia.controller;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.services.PersoonService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


//@Ignore
public class PersoonControllerTest {

    @Mock
    PersoonService persoonService;
    @InjectMocks
    PersoonController persoonController;
    private List<Persoon> personenLijst = new ArrayList<>();

    @Before
    public void setUp()  {
        initMocks(this);
        personenLijst.add(new Persoon(1, "Rense", "Houwing", "Voltawerk", "36", "8401 EN", "Gorredijk", "123456789", LocalDate.of(1967, 10, 12)));
    }

    @Test
    public void testPersonen() {

    }

    @Test
    public void testAddPersoon() {
        when(persoonService.getPersonen())
                .thenReturn(personenLijst);
        Persoon persoon = new Persoon(2, "Rense2", "Houwing2", "Voltawerk2", "36", "8401 EN", "Gorredijk", "123456789", LocalDate.of(1967, 10, 12));
        Persoon persoon2 = new Persoon(2, "Rense2", "Houwing2", "Voltawerk2", "36", "8401 EN", "Gorredijk", "123456789", LocalDate.of(1967, 10, 12));

        ResponseEntity responsPersoon = ResponseEntity.ok(persoon);
        assertEquals(responsPersoon, persoonController.addPersoon(persoon));
        assertEquals(responsPersoon.getBody(), persoonController.addPersoon(persoon).getBody());

    }
}