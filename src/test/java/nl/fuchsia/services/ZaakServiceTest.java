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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
        List<Feit> feiten = new ArrayList<>();
        List<Integer> feitnrs = new ArrayList<>();
        Feit feit = new Feit(1, "VBF-003", "Test", 4.00);
        feiten.add(feit);
        feitnrs.add(feit.getFeitNr());
        Zaak zaak = new Zaak( LocalDate.now(),"Leeuwarden",persoon,feiten);
        List<ZaakStatus> zaakStatussen = new ArrayList<>();
        List<Integer> zaakStatusnrs = new ArrayList<>();
        ZaakStatus zaakStatus = new ZaakStatus(LocalDate.now(),new Status(1, "Open"));
        zaakStatussen.add(zaakStatus);
        zaakStatussen.clear();
        zaakStatus.setZaakstatusnr(1);
        zaakStatussen.add(zaakStatus);
        zaak.setZaakStatus(zaakStatussen);
        zaakStatusnrs.add(zaakStatus.getZaakstatusnr());
        ZaakDto zaakDto = new ZaakDto(zaak.getZaaknr(),zaak.getOvertredingsdatum(),zaak.getPleeglocatie(),persoon.getPersoonnr(),feitnrs);
        zaakDto.setZaakstatusnr(zaakStatusnrs);
        when(persoonRepository.getPersoonById(persoon.getPersoonnr())).thenReturn(persoon);
        when(feitRepository.getFeitById(feit.getFeitNr())).thenReturn(feit);
        when(zaakDtoService.SetZaakStatusDto(any(),any())).thenReturn(zaakDto);
        when(zaakRepository.addZaak(any())).thenReturn(zaak);

		zaakService.addZaak(zaakDto);

		verify(persoonRepository).getPersoonById(persoon.getPersoonnr());
		verify(feitRepository).getFeitById(feit.getFeitNr());
		verify(zaakRepository).addZaak(zaak);
	}

    @Test
    public void testAddZaakPersoonDoesNotExist() {
        ZaakDto zaakAddDto = new ZaakDto(1, LocalDate.now(), "Leeuwarden", 1, new ArrayList<>(Arrays.asList(1)));
        Feit feit = new Feit(zaakAddDto.getFeitnrs().indexOf(0), "VBF-001", "Test", 4.00);
        when(feitRepository.getFeitById(1)).thenReturn(feit);

        assertThatThrownBy(() -> zaakService.addZaak(zaakAddDto))
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
    void testUpdZaakStatus() {
        Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
        List<Feit> feiten = new ArrayList<>();
        List<Integer> feitnrs = new ArrayList<>();
        Feit feit = new Feit(1, "VBF-003", "Test", 4.00);
        feiten.add(feit);
        feitnrs.add(feit.getFeitNr());
        Zaak zaak = new Zaak(1, LocalDate.now(),"Leeuwarden",persoon,feiten);
        List<ZaakStatus> zaakStatussen = new ArrayList<>();
        List<Integer> zaakStatusnrs = new ArrayList<>();
        ZaakStatus zaakStatus = new ZaakStatus(1,LocalDate.now(),new Status(1, "Open"),zaak);
        zaakStatussen.add(zaakStatus);
        zaakStatusnrs.add(zaakStatus.getZaakstatusnr());
        zaak.setZaakStatus(zaakStatussen);
        ZaakDto zaakDto = new ZaakDto(zaak.getZaaknr(),zaak.getOvertredingsdatum(),zaak.getPleeglocatie(),persoon.getPersoonnr(),feitnrs,zaakStatusnrs);
        Status status = new Status(1,"Open");

        when(zaakRepository.getZaakById(zaak.getZaaknr())).thenReturn(zaak);
        when(statusRepository.getStatusById(status.getStatusnr())).thenReturn(new Status(2, "In Behandeling"));

        assertThat(zaak.getZaakStatus()).hasSize(1);

        zaakService.updZaakStatus(zaak.getZaaknr(),new ZaakAddStatusDto(status.getStatusnr()));

        verify(persoonRepository).getPersoonById(persoon.getPersoonnr());
        verify(feitRepository).getFeitById(feit.getFeitNr());
        verify(zaakRepository).addZaak(zaak);

        assertThat(zaakDto.getZaakstatusnr()).hasSize(2);
    }

    @Test
    void testNotFoundZaakUpdZaakstatus() {
        Status status = new Status(1,"Open");
        when(statusRepository.getStatusById(status.getStatusnr())).thenReturn(status);
        ZaakAddStatusDto zaakAddStatusDto = new ZaakAddStatusDto(status.getStatusnr());

        assertThatThrownBy(() -> zaakService.updZaakStatus(5, zaakAddStatusDto)).isInstanceOf(NotFoundException.class).hasMessage("[ZaakNummer: 5 bestaat niet]");
    }

    @Test
    void testNotFoundStatusUpdZaakstatus() {
        Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
        List<Feit> feiten = new ArrayList<>();
        Feit feit = new Feit(1, "VBF-003", "Test", 4.00);
        feiten.add(feit);
        Zaak zaak = new Zaak(1, LocalDate.now(),"Leeuwarden",persoon,feiten);
        List<ZaakStatus> zaakStatussen = new ArrayList<>();
        ZaakStatus zaakStatus = new ZaakStatus(1,LocalDate.now(),new Status(1, "Open"),zaak);
        zaakStatussen.add(zaakStatus);
        zaak.setZaakStatus(zaakStatussen);
        ZaakAddStatusDto zaakAddStatusDto = new ZaakAddStatusDto(1);
        when(zaakRepository.getZaakById(zaak.getZaaknr())).thenReturn(zaak);

        assertThatThrownBy(() -> zaakService.updZaakStatus(zaak.getZaaknr(), zaakAddStatusDto)).isInstanceOf(NotFoundException.class).hasMessage("[StatusNummer: 1 bestaat niet]");
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