package nl.fuchsia.services;

import nl.fuchsia.dto.ZaakAddFeitDto;
import nl.fuchsia.dto.ZaakAddStatusDto;
import nl.fuchsia.dto.ZaakDto;
import nl.fuchsia.exceptionhandlers.NotFoundException;
import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.*;
import nl.fuchsia.repository.FeitRepository;
import nl.fuchsia.repository.PersoonRepository;
import nl.fuchsia.repository.StatusRepository;
import nl.fuchsia.repository.ZaakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ZaakServiceTest {

	@Mock
	private ZaakRepository zaakRepository;

	@Mock
	private StatusRepository statusRepository;

	@Mock
	private PersoonRepository persoonRepository;

	@Mock
	private FeitRepository feitRepository;

	@Mock
	private ZaakDtoService zaakDtoService;

	@InjectMocks
	private ZaakService zaakService;

	@BeforeEach
	public void setup() {
		initMocks(this);
	}

	@Test
	public void testAddZaak() {
		//Maak een persoon aan om toe te voegen aan zaak
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));

		//Maak een feit aan, zet deze in een lijst  om deze toe te voegen aan zaak
		Feit feit = new Feit(1, "VBF-003", "Test", 4.00);
		List<Feit> feiten = new ArrayList<>();
		feiten.add(feit);

		//Maakt een zaak aan om toe te voegen aan zaakStatus en nadat zaakStatus aan zaak is gekoppeld te gebruiken in verify
		Zaak zaak = new Zaak(LocalDate.now(), "Leeuwarden", persoon, feiten);

		//Maakt een zaakStatus om deze weer toe te voegen aan de zaak
		ZaakStatus zaakStatus = new ZaakStatus(null, LocalDate.now(), new Status(1, "Open"), zaak);
		List<ZaakStatus> zaakStatussen = new ArrayList<>();
		zaakStatussen.add(zaakStatus);
		zaak.setZaakstatus(zaakStatussen);

		//Maakt lijsten van feitnrs en zaakStatusnrs, om deze toe te kunnen voegen aan de in te voeren zaakDto
		List<Integer> feitnrs = new ArrayList<>();
		feitnrs.add(feit.getFeitnr());
		List<Integer> zaakStatusnrs = new ArrayList<>();
		zaakStatusnrs.add(zaakStatus.getZaakStatusnr());

		//Maakt een zaakDto voor de invoer van de functie
		ZaakDto zaakDto = new ZaakDto(zaak.getZaaknr(), zaak.getOvertredingsdatum(), zaak.getPleeglocatie(), persoon.getPersoonnr(), feitnrs, zaakStatusnrs);

		//Maakt een zaak die de repository/database normaal terug had gegeven
		Zaak savedZaak = new Zaak(1, LocalDate.now(), "Leeuwarden", persoon, feiten);
		List<ZaakStatus> savedZaakStatussen = new ArrayList<>();
		ZaakStatus savedZaakStatus = new ZaakStatus(1, LocalDate.now(), new Status(1, "Open"), zaak);
		savedZaakStatussen.add(savedZaakStatus);
		savedZaak.setZaakstatus(savedZaakStatussen);

		when(persoonRepository.findById(persoon.getPersoonnr())).thenReturn(java.util.Optional.of(persoon));
		when(zaakRepository.save((any()))).thenReturn(Optional.of(savedZaak));
		when(feitRepository.findById(feit.getFeitnr())).thenReturn(Optional.of(feit));

		zaakService.addZaak(zaakDto);

		verify(persoonRepository).findById(persoon.getPersoonnr());
		verify(feitRepository).findById(feit.getFeitnr());
		verify(zaakRepository).saveAndFlush(zaak);
	}

	@Test
	public void testAddZaakPersoonDoesNotExist() {
		ZaakDto zaakAddDto = new ZaakDto(1, LocalDate.now(), "Leeuwarden", 1, new ArrayList<>(Arrays.asList(1)));
		Feit feit = new Feit(zaakAddDto.getFeitnrs().indexOf(0), "VBF-001", "Test", 4.00);
		when(feitRepository.findById(1)).thenReturn(Optional.of(feit));

		assertThatThrownBy(() -> zaakService.addZaak(zaakAddDto)).isInstanceOf(NotFoundException.class).hasMessage("[ Persoonnr 1 bestaat niet]");
	}

	@Test
	public void testAddZaakFeitDoesNotExist() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
		ZaakDto zaakDto = new ZaakDto(1, LocalDate.of(2019, 2, 18), "Leeuwarden", 1, new ArrayList<>(Arrays.asList(1, 2)));
		when(persoonRepository.findById(persoon.getPersoonnr())).thenReturn(Optional.of(persoon));

		assertThatThrownBy(() -> zaakService.addZaak(zaakDto)).isInstanceOf(NotFoundException.class).hasMessage("[Feitnr 1 bestaat niet, Feitnr 2 bestaat niet]");

		when(feitRepository.findById(1)).thenReturn(Optional.of(new Feit(1, "VBF-001", "Test", 4.00)));

		assertThatThrownBy(() -> zaakService.addZaak(zaakDto)).isInstanceOf(NotFoundException.class).hasMessage("[Feitnr 2 bestaat niet]");
	}

	@Test
	public void testAddZaakFeitAndPersoonDoesNotExist() {
		ZaakDto zaakDto = new ZaakDto(1, LocalDate.of(2019, 2, 18), "Leeuwarden", 1, new ArrayList<>(Arrays.asList(1, 2)));

		assertThatThrownBy(() -> zaakService.addZaak(zaakDto)).isInstanceOf(NotFoundException.class).hasMessage("[ Persoonnr 1 bestaat niet, Feitnr 1 bestaat niet, Feitnr 2 bestaat niet]");
	}

	@Test
	public void testGetZaken() {
		zaakService.getZaken();

		verify(zaakRepository).findAll();
	}

	@Test
	void testGetZakenByPersoon() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
		when(persoonRepository.findById(1)).thenReturn(Optional.of(persoon));

		zaakService.getZakenByPersoon(persoon.getPersoonnr());

		verify(persoonRepository).findById(persoon.getPersoonnr());
		verify(zaakRepository).findAllByPersoon(persoon);
	}

	@Test
	public void testGetZakenByPersoonPersoonDoesNotExist() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));

		assertThatThrownBy(() -> zaakService.getZakenByPersoon(persoon.getPersoonnr())).isInstanceOf(NotFoundException.class).hasMessage("Persoonnr " + persoon.getPersoonnr() + " bestaat niet");
	}

	@Test
	void testUpdZaakStatus() {
		//Maak een persoon aan om toe te voegen aan zaak
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));

		//Maak een feit aan, zet deze in een lijst  om deze toe te voegen aan zaak
		Feit feit = new Feit(1, "VBF-003", "Test", 4.00);
		List<Feit> feiten = new ArrayList<>();
		feiten.add(feit);

		//Maakt een zaak aan om toe te voegen aan zaakStatus en nadat zaakStatus aan zaak is gekoppeld te gebruiken in verify
		Zaak zaak = new Zaak(LocalDate.now(), "Leeuwarden", persoon, feiten);

		//Maakt een zaakStatus om deze weer toe te voegen aan de zaak
		ZaakStatus zaakStatus = new ZaakStatus(null, LocalDate.now(), new Status(1, "Open"), zaak);
		List<ZaakStatus> zaakStatussen = new ArrayList<>();
		zaakStatussen.add(zaakStatus);
		zaak.setZaakstatus(zaakStatussen);

		//Maakt een lijst van feitnrs, om deze toe te kunnen voegen aan de te vekrijgen zaakDto
		List<Integer> feitnrs = new ArrayList<>();
		feitnrs.add(feit.getFeitnr());

		//Maakt een lijst van zaakStatusnrs, om deze toe te kunnen voegen aan de te vekrijgen zaakDto
		List<Integer> zaakStatusnrs = new ArrayList<>();
		ZaakStatus savedZaakStatus = new ZaakStatus(1, LocalDate.now(), new Status(1, "Open"), zaak);
		zaakStatusnrs.add(zaakStatus.getZaakStatusnr());

		//Maakt een zaak aan zoals die in de database is gewijzigd
		Zaak savedZaak = new Zaak(1, LocalDate.now(), "Leeuwarden", persoon, feiten);

		//Maakt een zaakDto aan en hangt hier een zaakstatus aan, deze wordt gebruikt om de uitvoer van setZaakDto te mocken
		ZaakDto zaakDto = new ZaakDto(savedZaak.getZaaknr(), zaak.getOvertredingsdatum(), zaak.getPleeglocatie(), persoon.getPersoonnr(), feitnrs, zaakStatusnrs);
		List<ZaakStatus> savedZaakStatussen = new ArrayList<>();
		savedZaakStatussen.add(savedZaakStatus);
		savedZaak.setZaakstatus(savedZaakStatussen);

		//Maakt de extra toe te voegen status aan
		Status status = new Status(2, "In Behandeling");

		when(zaakRepository.findById(zaak.getZaaknr())).thenReturn(Optional.of(zaak));
		when(statusRepository.findById(status.getStatusnr())).thenReturn(Optional.of(status));
		when(zaakDtoService.setZaakDto(zaak)).thenReturn(zaakDto);

		zaakService.updateZaakStatus(zaak.getZaaknr(), new ZaakAddStatusDto(status.getStatusnr()));

		verify(statusRepository).findById(status.getStatusnr());
		verify(zaakRepository).findById(zaak.getZaaknr());
		verify(zaakDtoService).setZaakDto(zaak);
	}

	@Test
	void testNotFoundZaakUpdZaakstatus() {
		Status status = new Status(1, "Open");
		ZaakAddStatusDto zaakAddStatusDto = new ZaakAddStatusDto(status.getStatusnr());
		when(statusRepository.findById(status.getStatusnr())).thenReturn(Optional.of(status));

		assertThatThrownBy(() -> zaakService.updateZaakStatus(5, zaakAddStatusDto)).isInstanceOf(NotFoundException.class).hasMessage("[ZaakNummer: 5 bestaat niet]");
	}

	@Test
	void testNotFoundStatusUpdZaakstatus() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));

		Feit feit = new Feit(1, "VBF-003", "Test", 4.00);
		List<Feit> feiten = new ArrayList<>();
		feiten.add(feit);

		Zaak zaak = new Zaak(1, LocalDate.now(), "Leeuwarden", persoon, feiten);

		ZaakStatus zaakStatus = new ZaakStatus(1, LocalDate.now(), new Status(1, "Open"), zaak);
		List<ZaakStatus> zaakStatussen = new ArrayList<>();
		zaakStatussen.add(zaakStatus);
		zaak.setZaakstatus(zaakStatussen);

		ZaakAddStatusDto zaakAddStatusDto = new ZaakAddStatusDto(1);

		Status status = new Status(2, "In Behandeling");

		when(statusRepository.findById(status.getStatusnr())).thenReturn(Optional.of(status));
		when(zaakRepository.findById(zaak.getZaaknr())).thenReturn(Optional.of(zaak));

		assertThatThrownBy(() -> zaakService.updateZaakStatus(zaak.getZaaknr(), zaakAddStatusDto)).isInstanceOf(NotFoundException.class).hasMessage("[StatusNummer: 1 bestaat niet]");
	}

	@Test
	public void testUpdZaakFeit() {
		//Maak een persoon aan om toe te voegen aan zaak
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));

		//Maak een feit aan, zet deze in een lijst  om deze toe te voegen aan zaak
		Feit feit = new Feit(1, "VBF-001", "Te hard gereden hoor...", 45.0);
		List<Feit> feiten = new ArrayList<>();
		feiten.add(feit);

		//Maakt een zaak aan om toe te voegen aan zaakStatus en nadat zaakStatus aan zaak is gekoppeld te gebruiken
		Zaak zaak = new Zaak(LocalDate.of(2019, 2, 18), "Leeuwarden", persoon, feiten);

		//Maakt een zaakStatus om deze weer toe te voegen aan de zaak
		ZaakStatus zaakstatus = new ZaakStatus(1, LocalDate.now(), new Status(1, "Open"), zaak);
		List<ZaakStatus> listZaakStatus = new ArrayList<>();
		listZaakStatus.add(zaakstatus);
		zaak.setZaakstatus(listZaakStatus);

		// Maakt een zaakAddFeitDto, voegt deze aan de lijst toe
		ZaakAddFeitDto zaakAddFeitDto = new ZaakAddFeitDto(2);
		List<ZaakAddFeitDto> listZaakAddFeitDto = new ArrayList<>();
		listZaakAddFeitDto.add(zaakAddFeitDto);

		//Maakt het toe te voegen feit
		Feit nieuwFeit = new Feit(2, "VBF-002", "Echt te hard gereden", 95.0);

		// Maakt eem zaakDto, deze is de returnwaarde van setZaakDto
		ZaakDto zaakDto = new ZaakDto(zaak.getZaaknr(), zaak.getOvertredingsdatum(), zaak.getPleeglocatie(), persoon.getPersoonnr(), Arrays.asList(feit.getFeitnr(), nieuwFeit.getFeitnr()));

		when(zaakRepository.findById(zaak.getZaaknr())).thenReturn(Optional.of(zaak));
		when(feitRepository.findById(nieuwFeit.getFeitnr())).thenReturn(Optional.of(nieuwFeit));
		when(zaakDtoService.setZaakDto(zaak)).thenReturn(zaakDto);

		assertThat(zaak.getFeiten()).hasSize(1);

		ZaakDto zaakDtoReturn = zaakService.updateZaakFeit(zaak.getZaaknr(), listZaakAddFeitDto);

		assertThat(zaakDtoReturn.getFeitnrs()).hasSize(2);

		verify(zaakRepository).findById(zaak.getZaaknr());
		verify(feitRepository, times(2)).findById(nieuwFeit.getFeitnr());
		verify(zaakDtoService).setZaakDto(zaak);
	}

	@Test
	public void testNotFoundZaakUpdZaakFeit() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));

		Feit feit = new Feit(1, "VBF-001", "Te hard gereden hoor...", 45.0);
		List<Feit> feiten = new ArrayList<>();
		feiten.add(feit);

		Zaak zaak = new Zaak(4, LocalDate.of(2019, 2, 18), "Leeuwarden", persoon, feiten);

		ZaakStatus zaakstatus = new ZaakStatus(LocalDate.now(), new Status(1, "Open"), zaak);
		List<ZaakStatus> listZaakStatus = new ArrayList<>();
		listZaakStatus.add(zaakstatus);
		zaak.setZaakstatus(listZaakStatus);

		ZaakAddFeitDto zaakAddFeitDto = new ZaakAddFeitDto(2);
		List<ZaakAddFeitDto> listZaakAddFeitDto = new ArrayList<>();
		listZaakAddFeitDto.add(zaakAddFeitDto);

		Feit feitTwee = new Feit(2, "VBF-002", "Echt te hard gereden", 95.0);

		when(zaakRepository.findById(zaak.getZaaknr())).thenReturn(Optional.of(zaak));
		when(feitRepository.findById(feitTwee.getFeitnr())).thenReturn(Optional.of(feitTwee));

		assertThat(zaak.getFeiten()).hasSize(1);
		assertThatThrownBy(() -> zaakService.updateZaakFeit(5, listZaakAddFeitDto)).isInstanceOf(NotFoundException.class).hasMessage("[zaakNummer: 5 bestaat niet, geen feit(en) toegevoegd]");
	}

	@Test
	public void testNotFoundFeit() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));

		Feit feit = new Feit(1, "VBF-001", "Te hard gereden hoor...", 45.0);
		List<Feit> feiten = new ArrayList<>();
		feiten.add(feit);

		Zaak zaak = new Zaak(4, LocalDate.of(2019, 2, 18), "Leeuwarden", persoon, feiten);

		List<ZaakStatus> listZaakStatus = new ArrayList<>();
		ZaakStatus zaakstatus = new ZaakStatus(LocalDate.now(), new Status(1, "Open"), zaak);
		listZaakStatus.add(zaakstatus);
		zaak.setZaakstatus(listZaakStatus);

		ZaakAddFeitDto zaakAddFeitDto = new ZaakAddFeitDto(2);
		List<ZaakAddFeitDto> listZaakAddFeitDto = new ArrayList<>();
		listZaakAddFeitDto.add(zaakAddFeitDto);

		Feit feitTwee = new Feit(2, "VBF-002", "Echt te hard gereden", 95.0);

		when(zaakRepository.findById(zaak.getZaaknr())).thenReturn(Optional.of(zaak));
		when(feitRepository.findById(feitTwee.getFeitnr())).thenReturn(Optional.of(feitTwee));

		assertThat(zaak.getFeiten()).hasSize(1);

		ZaakAddFeitDto zaakAddFeitDto1 = new ZaakAddFeitDto(3);
		listZaakAddFeitDto.add(zaakAddFeitDto1);

		assertThatThrownBy(() -> zaakService.updateZaakFeit(zaak.getZaaknr(), listZaakAddFeitDto)).isInstanceOf(NotFoundException.class)
			.hasMessage("[feitNummer: 3 bestaat niet, geen feit(en) toegevoegd]");
	}

	@Test
	public void testFeitAlreadyAdded() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));

		Feit feit = new Feit(1, "VBF-001", "Te hard gereden hoor...", 45.0);
		List<Feit> feiten = new ArrayList<>();
		feiten.add(feit);

		Zaak zaak = new Zaak(4, LocalDate.of(2019, 2, 18), "Leeuwarden", persoon, feiten);
		List<ZaakStatus> listZaakStatus = new ArrayList<>();
		ZaakStatus zaakstatus = new ZaakStatus(LocalDate.now(), new Status(1, "Open"), zaak);
		listZaakStatus.add(zaakstatus);
		zaak.setZaakstatus(listZaakStatus);

		ZaakAddFeitDto zaakAddFeitDto = new ZaakAddFeitDto(1);
		List<ZaakAddFeitDto> listZaakAddFeitDto = new ArrayList<>();
		listZaakAddFeitDto.add(zaakAddFeitDto);

		when(zaakRepository.findById(zaak.getZaaknr())).thenReturn(Optional.of(zaak));
		when(feitRepository.findById(feit.getFeitnr())).thenReturn(Optional.of(feit));

		assertThat(zaak.getFeiten()).hasSize(1);
		assertThatThrownBy(() -> zaakService.updateZaakFeit(zaak.getZaaknr(), listZaakAddFeitDto)).isInstanceOf(UniekVeldException.class)
			.hasMessage("[feitNummer: 1 is reeds toegevoegd aan deze zaak, geen feit(en) toegevoegd]");
	}
}
