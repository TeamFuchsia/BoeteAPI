package nl.fuchsia.services;

import java.time.LocalDate;

import nl.fuchsia.dto.PersoonEditDto;
import nl.fuchsia.exceptionhandlers.NotFoundException;
import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.Feit;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.repository.FeitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.transaction.TransactionSystemException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class FeitServiceTest {
	@Mock
	private FeitRepository feitRepository;
	@InjectMocks
	private FeitService feitService;

	@BeforeEach
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testAddFeit() {
		Feit feit = new Feit();

		feitService.addFeit(feit);

		verify(feitRepository).addFeit(feit);
	}

	@Test
	public void testNonUniekFeitcodeExeption() {
		when(feitRepository.addFeit(any(Feit.class))).thenThrow(new TransactionSystemException("TestException"));

		assertThatThrownBy(() -> feitService.addFeit(new Feit(1, "VBF-001", "Test", 500))).isInstanceOf(UniekVeldException.class).hasMessage("Feitcode: VBF-001 bestaat al in de database.");
	}

	@Test
	public void testGetFeiten() {
		feitService.getFeiten();

		verify(feitRepository).getFeiten();
	}

	/**
	 * Test of de methode updatePersoonByID in de persoonRepository wordt aangeroepen.
	 */
	@Test
	public void testUpdateFeitById() {
		Feit feit = new Feit(2, "VBF-002", "Test", 500);
		when(feitRepository.getFeitById(feit.getFeitNr())).thenReturn(feit);
		Feit updatedfeit = new Feit(2, "VBF-002", "Test", 5000);

		feitRepository.updateFeitById(updatedfeit);

		verify(feitRepository).getFeitById(feit.getFeitNr());
		verify(feitRepository).updateFeitById(updatedfeit);
	}

//	/**
//	 * Test controleert of het BSN bij het updaten/toevoegen al bestaat in de database.
//	 */
//	@Test
//	public void testNonUniekBsnExeption() {
//		when(persoonRepository.addPersoon(any(Persoon.class))).thenThrow(new TransactionSystemException("TestException"));
//
//		assertThatThrownBy(() -> persoonService.addPersoon(new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12))))
//				.isInstanceOf(UniekVeldException.class).hasMessage("BSN nummer: 123456789 bestaat reeds.");
//	}
//
//	/**
//	 * Test controleert of de te updaten persoonnr bestaat.
//	 */
//	@Test
//	public void testBestaanPersoonnr() {
//		when(persoonRepository.updatePersoonById(any(Persoon.class))).thenThrow(new TransactionSystemException("TestException"));
//
//		assertThatThrownBy(() -> persoonService.updatePersoonById(new PersoonEditDto(1, "Geert", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12))))
//				.isInstanceOf(NotFoundException.class).hasMessage("Persoonnummer: 1 bestaat niet!");
//	}
}
