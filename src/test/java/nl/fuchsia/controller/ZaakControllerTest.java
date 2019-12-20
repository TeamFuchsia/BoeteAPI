package nl.fuchsia.controller;

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
		zaakController.getZaken();

		verify(zaakService).getZaken();
	}

    @Test
    public void testAddZaak() {
        ZaakAddDto zaakAddDto = new ZaakAddDto();

        zaakController.addZaak(zaakAddDto);

        verify(zaakService).addZaak(zaakAddDto);
    }
}
