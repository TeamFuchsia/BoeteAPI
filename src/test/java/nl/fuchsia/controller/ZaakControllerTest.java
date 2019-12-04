package nl.fuchsia.controller;

import nl.fuchsia.model.Zaak;
import nl.fuchsia.services.ZaakService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ZaakControllerTest {

    @Mock
    ZaakService zaakService;
    @InjectMocks
    ZaakController zaakController;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testGetZaken() {
        zaakController.getZaken();

        verify(zaakService).getZaken();
    }

    @Test
    public void testAddZaak() {
        Zaak zaak = new Zaak();

        zaakController.addZaak(zaak);

        verify(zaakService).addZaak(zaak);
    }
}
