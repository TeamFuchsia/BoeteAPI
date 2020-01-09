package nl.fuchsia.controller;

import nl.fuchsia.dto.ZaakDto;
import nl.fuchsia.services.ZaakService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ZaakControllerTest {

	@Mock
	ZaakService zaakService;
	@InjectMocks
	ZaakController zaakController;

	@BeforeEach
	public void setup() {
		initMocks(this);
	}

	@Test
	public void testGetZaken() {
		zaakController.getZaken(null);

		verify(zaakService).getZaken();
	}

    @Test
    public void testAddZaak() {
        ZaakDto zaakDto = new ZaakDto();

        zaakController.addZaak(zaakDto);

        verify(zaakService).addZaak(zaakDto);
    }

	@Test
	void testGetZakenByPersoon() {
		int persoonnr = 1;

		zaakController.getZaken(persoonnr);

		verify(zaakService).getZakenByPersoon(persoonnr);
	}
}