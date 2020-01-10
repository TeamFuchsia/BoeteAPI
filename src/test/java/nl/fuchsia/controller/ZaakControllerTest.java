package nl.fuchsia.controller;

import nl.fuchsia.dto.ZaakAddStatusDto;
import nl.fuchsia.dto.ZaakDto;
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

	@Test
	void testAddFeitToZaak() {
		List <ZaakAddFeitDto> listZaakAddFeitDto = new ArrayList<>();
		ZaakAddFeitDto zaakAddFeitDto = new ZaakAddFeitDto();
		listZaakAddFeitDto.add(zaakAddFeitDto);
		Integer zaakNr = 4;

		zaakController.updZaakFeit(zaakNr, listZaakAddFeitDto);

		verify(zaakService).updZaakFeit(zaakNr, listZaakAddFeitDto);
	}

    @Test
    void testGetZaakById(){
        zaakService.getZaakById(1);

        verify(zaakService).getZaakById(1);
    }

    @Test
    void addZaakStatus() {
        ZaakAddStatusDto zaakAddStatusDto = new ZaakAddStatusDto();

        zaakController.addZaakStatus(1, zaakAddStatusDto);

        verify(zaakService).updZaakStatus(1,zaakAddStatusDto);
    }
}