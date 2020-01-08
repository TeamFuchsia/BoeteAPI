//package nl.fuchsia.services;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import nl.fuchsia.dto.ZaakAddDto;
//import nl.fuchsia.exceptionhandlers.NotFoundException;
//import nl.fuchsia.model.Feit;
//import nl.fuchsia.model.Persoon;
//import nl.fuchsia.model.Status;
//import nl.fuchsia.model.Zaak;
//import nl.fuchsia.repository.FeitRepository;
//import nl.fuchsia.repository.PersoonRepository;
//import nl.fuchsia.repository.ZaakRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.initMocks;
//
//public class ZaakServiceTest {
//
//	@Mock
//	private ZaakRepository zaakRepository;
//
//	@Mock
//	private PersoonRepository persoonRepository;
//
//	@Mock
//	private FeitRepository feitRepository;
//
//	@InjectMocks
//	private ZaakService zaakService;
//
//	@BeforeEach
//	public void setup() {
//		initMocks(this);
//	}
//
//	@Test
//	public void testAddZaak() {
//		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
//		List<Feit> feiten = new ArrayList<>();
//		when(persoonRepository.getPersoonById(1)).thenReturn(persoon);
//		ZaakAddDto zaakAddDto = new ZaakAddDto(1, LocalDate.of(2019, 2, 18), "Leeuwarden", 1, new ArrayList<>(Arrays.asList(1, 2)));
//		List<Integer> feitnrs = zaakAddDto.getFeitnrs();
//		for (int feitnr : feitnrs) {
//			Feit feit = new Feit(feitnr, "VBF-003", "Test", 4.00);
//			when(feitRepository.getFeitById(feitnr)).thenReturn(feit);
//			feiten.add(feit);
//		}
//		Zaak zaak = new Zaak(LocalDate.of(2019, 2, 18), "Leeuwarden", persoon, feiten,new ArrayList<Status>(Arrays.asList()));
//
//		zaakService.addZaak(zaakAddDto);
//
//		verify(persoonRepository).getPersoonById(1);
//		verify(feitRepository).getFeitById(1);
//		verify(zaakRepository).addZaak(zaak);
//	}
//
//	@Test
//	public void testAddZaakPersoonDoesNotExist() {
//		ZaakAddDto zaakAddDto = new ZaakAddDto(1, LocalDate.of(2019, 2, 18), "Leeuwarden", 1, new ArrayList<>(Arrays.asList(1)));
//		Feit feit = new Feit(zaakAddDto.getFeitnrs().indexOf(0), "VBF-001", "Test", 4.00);
//		when(feitRepository.getFeitById(1)).thenReturn(feit);
//
//		assertThatThrownBy(() -> zaakService.addZaak(zaakAddDto))
//			.isInstanceOf(NotFoundException.class).hasMessage("[ Persoonnr 1 bestaat niet]");
//	}
//
//	@Test
//	public void testAddZaakFeitDoesNotExist() {
//		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
//		ZaakAddDto zaakAddDto = new ZaakAddDto(1, LocalDate.of(2019, 2, 18), "Leeuwarden", 1, new ArrayList<>(Arrays.asList(1, 2)));
//		when(persoonRepository.getPersoonById(1)).thenReturn(persoon);
//
//		assertThatThrownBy(() -> zaakService.addZaak(zaakAddDto))
//			.isInstanceOf(NotFoundException.class).hasMessage("[Feitnr 1 bestaat niet, Feitnr 2 bestaat niet]");
//
//		when(feitRepository.getFeitById(1)).thenReturn(new Feit(1, "VBF-001", "Test", 4.00));
//
//		assertThatThrownBy(() -> zaakService.addZaak(zaakAddDto))
//			.isInstanceOf(NotFoundException.class).hasMessage("[Feitnr 2 bestaat niet]");
//	}
//
//	@Test
//	public void testAddZaakFeitAndPersoonDoesNotExist() {
//		ZaakAddDto zaakAddDto = new ZaakAddDto(1, LocalDate.of(2019, 2, 18), "Leeuwarden", 1, new ArrayList<>(Arrays.asList(1, 2)));
//
//		assertThatThrownBy(() -> zaakService.addZaak(zaakAddDto))
//				.isInstanceOf(NotFoundException.class).hasMessage("[ Persoonnr 1 bestaat niet, Feitnr 1 bestaat niet, Feitnr 2 bestaat niet]");
//	}
//
//	@Test
//	public void testGetZaken() {
//		zaakService.getZaken();
//
//		verify(zaakRepository).getZaken();
//	}
//
//	@Test
//	void testGetZakenByPersoon() {
//		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
//		when(persoonRepository.getPersoonById(1)).thenReturn(persoon);
//
//		zaakService.getZakenByPersoon(persoon.getPersoonnr());
//
//		verify(persoonRepository).getPersoonById(persoon.getPersoonnr());
//		verify(zaakRepository).getZakenByPersoon(persoon);
//	}
//
//	@Test
//	public void testGetZakenByPersoonPersoonDoesNotExist() {
//		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
//
//		assertThatThrownBy(() -> zaakService.getZakenByPersoon(persoon.getPersoonnr()))
//				.isInstanceOf(NotFoundException.class).hasMessage("Persoonnr " + persoon.getPersoonnr() + " bestaat niet");
//	}
//}