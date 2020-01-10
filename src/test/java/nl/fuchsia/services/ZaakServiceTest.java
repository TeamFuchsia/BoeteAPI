package nl.fuchsia.services;

import nl.fuchsia.dto.ZaakDto;
import nl.fuchsia.dto.ZaakAddFeitDto;
import nl.fuchsia.exceptionhandlers.NotFoundException;
import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.*;
import nl.fuchsia.repository.FeitRepository;
import nl.fuchsia.repository.PersoonRepository;
import nl.fuchsia.repository.ZaakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ZaakServiceTest {

	@Mock
	private ZaakRepository zaakRepository;

	@Mock
	private PersoonRepository persoonRepository;

	@Mock
	private FeitRepository feitRepository;

	@InjectMocks
	private ZaakService zaakService;

	@BeforeEach
	public void setup() {
		initMocks(this);
	}

//	@Test
//	public void testAddZaak() {
//		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
//		List<Feit> feiten = new ArrayList<>();
//		when(persoonRepository.getPersoonById(1)).thenReturn(persoon);
//		ZaakDto zaakDto = new ZaakDto(1, LocalDate.of(2019, 2, 18), "Leeuwarden", 1, new ArrayList<>(Arrays.asList(1, 2)));
//		List<Integer> feitnrs = zaakDto.getFeitnrs();
//		for (int feitnr : feitnrs) {
//			Feit feit = new Feit(feitnr, "VBF-003", "Test", 4.00);
//			when(feitRepository.getFeitById(feitnr)).thenReturn(feit);
//			feiten.add(feit);
//		}
//		Zaak zaak = new Zaak(LocalDate.of(2019, 2, 18), "Leeuwarden", persoon, feiten);
//
//		zaakService.addZaak(zaakDto);
//
//		verify(persoonRepository).getPersoonById(1);
//		verify(feitRepository).getFeitById(1);
//		verify(zaakRepository).addZaak(zaak);
//	}

	@Test
	public void testAddZaakPersoonDoesNotExist() {
		ZaakDto zaakDto = new ZaakDto(1, LocalDate.of(2019, 2, 18), "Leeuwarden", 1, new ArrayList<>(Arrays.asList(1)));
		Feit feit = new Feit(zaakDto.getFeitnrs().indexOf(0), "VBF-001", "Test", 4.00);
		when(feitRepository.getFeitById(1)).thenReturn(feit);

		assertThatThrownBy(() -> zaakService.addZaak(zaakDto))
			.isInstanceOf(NotFoundException.class).hasMessage("[ Persoonnr 1 bestaat niet]");
	}

	@Test
	public void testAddZaakFeitDoesNotExist() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
		ZaakDto zaakDto = new ZaakDto(1, LocalDate.of(2019, 2, 18), "Leeuwarden", 1, new ArrayList<>(Arrays.asList(1, 2)));
		when(persoonRepository.getPersoonById(1)).thenReturn(persoon);

		assertThatThrownBy(() -> zaakService.addZaak(zaakDto))
			.isInstanceOf(NotFoundException.class).hasMessage("[Feitnr 1 bestaat niet, Feitnr 2 bestaat niet]");

		when(feitRepository.getFeitById(1)).thenReturn(new Feit(1, "VBF-001", "Test", 4.00));

		assertThatThrownBy(() -> zaakService.addZaak(zaakDto))
			.isInstanceOf(NotFoundException.class).hasMessage("[Feitnr 2 bestaat niet]");
	}

	@Test
	public void testAddZaakFeitAndPersoonDoesNotExist() {
		ZaakDto zaakDto = new ZaakDto(1, LocalDate.of(2019, 2, 18), "Leeuwarden", 1, new ArrayList<>(Arrays.asList(1, 2)));

		assertThatThrownBy(() -> zaakService.addZaak(zaakDto))
			.isInstanceOf(NotFoundException.class).hasMessage("[ Persoonnr 1 bestaat niet, Feitnr 1 bestaat niet, Feitnr 2 bestaat niet]");
	}

	@Test
	public void testGetZaken() {
		zaakService.getZaken();

		verify(zaakRepository).getZaken();
	}

	@Test
	void testGetZakenByPersoon() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
		when(persoonRepository.getPersoonById(1)).thenReturn(persoon);

		zaakService.getZakenByPersoon(persoon.getPersoonnr());

		verify(persoonRepository).getPersoonById(persoon.getPersoonnr());
		verify(zaakRepository).getZakenByPersoon(persoon);
	}

	@Test
	public void testGetZakenByPersoonPersoonDoesNotExist() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));

		assertThatThrownBy(() -> zaakService.getZakenByPersoon(persoon.getPersoonnr()))
			.isInstanceOf(NotFoundException.class).hasMessage("Persoonnr " + persoon.getPersoonnr() + " bestaat niet");
	}

	@Test
	public void testUpdZaakFeit() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
		List<Feit> feiten = new ArrayList<>();
		Feit feit = new Feit(1, "VBF-001", "Te hard gereden hoor...", 45.0);
		feiten.add(feit);
		List<ZaakStatus> listZaakStatus = new ArrayList<>();
		Zaak zaak = new Zaak(LocalDate.of(2019, 2, 18),"Leeuwarden",persoon,feiten,listZaakStatus);
		ZaakStatus zaakstatus = new ZaakStatus(1, LocalDate.now(),new Status(1, "Open"),zaak);
		listZaakStatus.add(zaakstatus);
		zaak.setZaakStatus(listZaakStatus);
		ZaakAddFeitDto zaakAddFeitDto = new ZaakAddFeitDto(2);
		List<ZaakAddFeitDto> listZaakAddFeitDto = new ArrayList<>();
		listZaakAddFeitDto.add(zaakAddFeitDto);
		Feit feitTwee = new Feit(2, "VBF-002", "Echt te hard gereden", 95.0);
		when(zaakRepository.getZaakById(zaak.getZaaknr())).thenReturn(zaak);
		when(feitRepository.getFeitById(feitTwee.getFeitNr())).thenReturn(feitTwee);
		assertThat(zaak.getFeiten()).hasSize(1);

		ZaakDto zaakDtoReturn = zaakService.updZaakFeit(zaak.getZaaknr(), listZaakAddFeitDto);
		assertThat(zaakDtoReturn.getFeitnrs()).hasSize(2);
	}

	@Test
	public void testNotFoundZaakUpdZaakFeit() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
		List<Feit> feiten = new ArrayList<>();
		Feit feit = new Feit(1, "VBF-001", "Te hard gereden hoor...", 45.0);
		feiten.add(feit);
		List<ZaakStatus> listZaakStatus = new ArrayList<>();
		Zaak zaak = new Zaak(4,LocalDate.of(2019, 2, 18),"Leeuwarden",persoon,feiten,listZaakStatus);
		ZaakStatus zaakstatus = new ZaakStatus(LocalDate.now(),new Status(1, "Open"),zaak);
		listZaakStatus.add(zaakstatus);
		zaak.setZaakStatus(listZaakStatus);
		ZaakAddFeitDto zaakAddFeitDto = new ZaakAddFeitDto(2);
		List<ZaakAddFeitDto> listZaakAddFeitDto = new ArrayList<>();
		listZaakAddFeitDto.add(zaakAddFeitDto);
		Feit feitTwee = new Feit(2, "VBF-002", "Echt te hard gereden", 95.0);
		when(zaakRepository.getZaakById(zaak.getZaaknr())).thenReturn(zaak);
		when(feitRepository.getFeitById(feitTwee.getFeitNr())).thenReturn(feitTwee);
		assertThat(zaak.getFeiten()).hasSize(1);

		assertThatThrownBy(() -> zaakService.updZaakFeit(5, listZaakAddFeitDto)).isInstanceOf(NotFoundException.class).hasMessage("[zaakNummer: 5 bestaat niet, geen feit(en) toegevoegd]");
	}

	@Test
	public void testNotFoundFeit() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
		List<Feit> feiten = new ArrayList<>();
		Feit feit = new Feit(1, "VBF-001", "Te hard gereden hoor...", 45.0);
		feiten.add(feit);
		List<ZaakStatus> listZaakStatus = new ArrayList<>();
		Zaak zaak = new Zaak(4,LocalDate.of(2019, 2, 18),"Leeuwarden",persoon,feiten,listZaakStatus);
		ZaakStatus zaakstatus = new ZaakStatus(LocalDate.now(),new Status(1, "Open"),zaak);
		listZaakStatus.add(zaakstatus);
		zaak.setZaakStatus(listZaakStatus);
		ZaakAddFeitDto zaakAddFeitDto = new ZaakAddFeitDto(2);
		List<ZaakAddFeitDto> listZaakAddFeitDto = new ArrayList<>();
		listZaakAddFeitDto.add(zaakAddFeitDto);
		Feit feitTwee = new Feit(2, "VBF-002", "Echt te hard gereden", 95.0);
		when(zaakRepository.getZaakById(zaak.getZaaknr())).thenReturn(zaak);
		when(feitRepository.getFeitById(feitTwee.getFeitNr())).thenReturn(feitTwee);
		assertThat(zaak.getFeiten()).hasSize(1);

		ZaakAddFeitDto zaakAddFeitDto1 = new ZaakAddFeitDto(3);
		listZaakAddFeitDto.add(zaakAddFeitDto1);
		assertThatThrownBy(() -> zaakService.updZaakFeit(zaak.getZaaknr(), listZaakAddFeitDto)).isInstanceOf(NotFoundException.class).hasMessage("[feitNummer: 3 bestaat niet, geen feit(en) toegevoegd]");
	}

	@Test
	public void testFeitAlreadyAdded() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
		List<Feit> feiten = new ArrayList<>();
		Feit feit = new Feit(1, "VBF-001", "Te hard gereden hoor...", 45.0);
		feiten.add(feit);
		List<ZaakStatus> listZaakStatus = new ArrayList<>();
		Zaak zaak = new Zaak(4,LocalDate.of(2019, 2, 18),"Leeuwarden",persoon,feiten,listZaakStatus);
		ZaakStatus zaakstatus = new ZaakStatus(LocalDate.now(),new Status(1, "Open"),zaak);
		listZaakStatus.add(zaakstatus);
		zaak.setZaakStatus(listZaakStatus);
		ZaakAddFeitDto zaakAddFeitDto = new ZaakAddFeitDto(1);
		List<ZaakAddFeitDto> listZaakAddFeitDto = new ArrayList<>();
		listZaakAddFeitDto.add(zaakAddFeitDto);
		when(zaakRepository.getZaakById(zaak.getZaaknr())).thenReturn(zaak);
		when(feitRepository.getFeitById(feit.getFeitNr())).thenReturn(feit);
		assertThat(zaak.getFeiten()).hasSize(1);

		assertThatThrownBy(() -> zaakService.updZaakFeit(zaak.getZaaknr(), listZaakAddFeitDto)).isInstanceOf(UniekVeldException.class).hasMessage("[feitNummer: 1 is reeds toegevoegd aan deze zaak, geen feit(en) toegevoegd]");
	}
}
