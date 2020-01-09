package nl.fuchsia.controller;

import nl.fuchsia.dto.ZaakAddDto;
import nl.fuchsia.dto.ZaakAddFeitDto;
import nl.fuchsia.services.ZaakService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

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
        ZaakAddDto zaakAddDto = new ZaakAddDto();

        zaakController.addZaak(zaakAddDto);

        verify(zaakService).addZaak(zaakAddDto);
    }

	@Test
	void testGetZakenByPersoon() {
		int persoonnr = 1;

		zaakController.getZaken(persoonnr);

		verify(zaakService).getZakenByPersoon(persoonnr);
	}

	@Test
	void testAddFeitToZaak() {
		List <ZaakAddFeitDto> listZaakAddFeitDto = new ArrayList<>();
		ZaakAddFeitDto zaakAddFeitDto = new ZaakAddFeitDto();
		listZaakAddFeitDto.add(zaakAddFeitDto);
		Integer zaakNr = 4;

		zaakController.updZaakFeit(zaakNr, listZaakAddFeitDto);

		verify(zaakService).updZaakFeit(zaakNr, listZaakAddFeitDto);
	}
}
