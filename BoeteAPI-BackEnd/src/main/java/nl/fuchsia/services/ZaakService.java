package nl.fuchsia.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import nl.fuchsia.dto.ZaakAddFeitDto;
import nl.fuchsia.dto.ZaakAddStatusDto;
import nl.fuchsia.dto.ZaakDto;
import nl.fuchsia.exceptionhandlers.NotFoundException;
import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.Feit;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.model.Status;
import nl.fuchsia.model.Zaak;
import nl.fuchsia.model.ZaakStatus;
import nl.fuchsia.repository.FeitRepository;
import nl.fuchsia.repository.PersoonRepository;
import nl.fuchsia.repository.StatusRepository;
import nl.fuchsia.repository.ZaakRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ZaakService {

    private ZaakRepository zaakRepository;
    private PersoonRepository persoonRepository;
    private FeitRepository feitRepository;
    private StatusRepository statusRepository;
    private ZaakDtoService zaakDtoService;

    public ZaakService(ZaakRepository zaakRepository, PersoonRepository persoonRepository, FeitRepository feitRepository, StatusRepository statusRepository, ZaakDtoService zaakDtoService) {
        this.zaakRepository = zaakRepository;
        this.persoonRepository = persoonRepository;
        this.feitRepository = feitRepository;
        this.statusRepository = statusRepository;
        this.zaakDtoService = zaakDtoService;
    }

    /**
     * Voegt een nieuwe zaak toe inclusief een persoon en minimaal 1 feit.
     *
     * @param zaakDto de ingevoerde zaakDto
     * @return de gemaakte zaak inclusief persoon en feit(en).
     */
    public ZaakDto addZaak(ZaakDto zaakDto) {

        List<String> exceptions = new ArrayList<>();

        Persoon persoon = persoonRepository.getPersoonById(zaakDto.getPersoonnr());

        if (persoon == null) {
            exceptions.add(" Persoonnr " + zaakDto.getPersoonnr() + " bestaat niet");
        }
        List<Feit> feiten = new ArrayList<>();
        for (int feitNr : zaakDto.getFeitnrs()) {
            Feit feit = feitRepository.getFeitById(feitNr);
            if (feit == null) {
                exceptions.add("Feitnr " + feitNr + " bestaat niet");
            } else
                feiten.add(feit);
        }
        if (exceptions.size() > 0) {
            throw new NotFoundException(exceptions.toString());
        }

        Zaak zaak = new Zaak(zaakDto.getOvertredingsdatum(), zaakDto.getPleeglocatie(), persoon, feiten);
        List<ZaakStatus> zaakStatussen = new ArrayList<>();
        ZaakStatus zaakStatus = new ZaakStatus(LocalDate.now(), new Status(1, "Open"), zaak);
        zaakStatussen.add(zaakStatus);

        zaak.setZaakStatus(zaakStatussen);
        Zaak savedZaak = zaakRepository.addZaak(zaak);

        zaakDto = zaakDtoService.setZaakDto(savedZaak);

        return zaakDto;
    }

    /**
     * Voegt 1 zaakStatus toe aan een bestaande zaak en hier een nieuwe historie voor statussen aan van de zaak.
     *
     * @param zaakNr           de betreffende bestaande zaak
     * @param zaakAddStatusDto de toe te voegen status
     * @return de geupdate zaak.
     */
    @Transactional
    public ZaakDto updZaakStatus(Integer zaakNr, ZaakAddStatusDto zaakAddStatusDto) {
        List<String> notFoundExceptions = new ArrayList<>();
        Status status = statusRepository.getStatusById(zaakAddStatusDto.getStatusNr());
        Zaak zaak = zaakRepository.getZaakById(zaakNr);

        if (zaak == null) {
            notFoundExceptions.add("ZaakNummer: " + zaakNr + " bestaat niet");
        }

        if (status == null) {
            notFoundExceptions.add("StatusNummer: " + zaakAddStatusDto.getStatusNr() + " bestaat niet");
        }

        if (notFoundExceptions.size() > 0) {
            throw new NotFoundException(notFoundExceptions.toString());
        }

        ZaakStatus zaakStatus = new ZaakStatus(LocalDate.now(), status, zaak);

        List<ZaakStatus> zaakStatussen = zaak.getZaakStatus();
        zaakStatussen.add(zaakStatus);
        zaak.setZaakStatus(zaakStatussen);
        zaakRepository.addZaak(zaak);

        ZaakDto zaakDto = zaakDtoService.setZaakDto(zaak);

        return zaakDto;
    }

    public List<ZaakDto> getZaken() {
        List<Zaak> zaken = zaakRepository.getZaken();
        List<ZaakDto> zaakDtos = new ArrayList<>();
        for (Zaak zaak : zaken) {
            zaakDtos.add(zaakDtoService.setZaakDto(zaak));
        }
        return zaakDtos;
    }

    public ZaakDto getZaakById(Integer zaakNr) {

        if (zaakRepository.getZaakById(zaakNr) == null) {
            throw new NotFoundException("ZaakNummer: " + zaakNr + " bestaat niet");
        }

        Zaak zaak = zaakRepository.getZaakById(zaakNr);

        return zaakDtoService.setZaakDto(zaak);
    }

    public List<ZaakDto> getZakenByPersoon(Integer persoonnr) {

        Persoon persoon = persoonRepository.getPersoonById(persoonnr);

        if (persoon == null) {
            throw new NotFoundException("Persoonnr " + persoonnr + " bestaat niet");
        }
        List<Zaak> zaken = zaakRepository.getZakenByPersoon(persoon);
        List<ZaakDto> zaakDtos = new ArrayList<>();
        for (Zaak zaak : zaken) {
            zaakDtos.add(zaakDtoService.setZaakDto(zaak));
        }

        return zaakDtos;
    }

    /**
     * Voegt 1 of meer bestaande feiten toe aan een bestaande zaak.
     *
     * @param zaakNr             de betreffende bestaande zaak
     * @param listZaakAddFeitDto de toe te voegen feit(en)
     * @return de geupdate zaak.
     */
    @Transactional
    public ZaakDto updZaakFeit(Integer zaakNr, List<ZaakAddFeitDto> listZaakAddFeitDto) {
        List<String> notFoundExceptions = new ArrayList<>();
        List<String> uniekVeldExceptions = new ArrayList<>();
        Zaak zaak = zaakRepository.getZaakById(zaakNr);

        if (zaak == null) {
            notFoundExceptions.add("zaakNummer: " + zaakNr + " bestaat niet");
        }
        for (ZaakAddFeitDto zaakAddFeitDto : listZaakAddFeitDto) {
            if (feitRepository.getFeitById(zaakAddFeitDto.getFeitNr()) == null) {
                notFoundExceptions.add("feitNummer: " + zaakAddFeitDto.getFeitNr() + " bestaat niet");
            }
        }
        if (notFoundExceptions.size() > 0) {
            notFoundExceptions.add("geen feit(en) toegevoegd");
            throw new NotFoundException(notFoundExceptions.toString());
        }

        List<Feit> zaakFeiten = zaak.getFeiten();
        for (ZaakAddFeitDto zaakAddFeitDto : listZaakAddFeitDto) {
            int feitNrDto = zaakAddFeitDto.getFeitNr();
            for (Feit feit : zaakFeiten) {
                if (feit.getFeitNr() == feitNrDto) {
                    uniekVeldExceptions.add("feitNummer: " + zaakAddFeitDto.getFeitNr() + " is reeds toegevoegd aan deze zaak");
                }
            }
        }
        if (uniekVeldExceptions.size() > 0) {
            uniekVeldExceptions.add("geen feit(en) toegevoegd");
            throw new UniekVeldException(uniekVeldExceptions.toString());
        }
        for (ZaakAddFeitDto zaakAddFeitDto : listZaakAddFeitDto) {
            zaakFeiten.add(feitRepository.getFeitById(zaakAddFeitDto.getFeitNr()));
            zaak.setFeiten(zaakFeiten);
        }
        ZaakDto zaakDto = zaakDtoService.setZaakDto(zaak);
        return zaakDto;
    }
}