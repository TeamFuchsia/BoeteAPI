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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

		Optional<Persoon> persoon = persoonRepository.findById(zaakDto.getPersoonnr());
		if (!persoon.isPresent()) {
			exceptions.add(" Persoonnr " + zaakDto.getPersoonnr() + " bestaat niet");
		}
		List<Feit> feiten = new ArrayList<>();
		for (int feitNr : zaakDto.getFeitnrs()) {
			Optional<Feit> feit = feitRepository.findById(feitNr);
			if (!feit.isPresent()) {
				exceptions.add("Feitnr " + feitNr + " bestaat niet");
			} else
				feiten.add(feit.get());
		}
		if (exceptions.size() > 0) {
			throw new NotFoundException(exceptions.toString());
		}

		Zaak zaak = new Zaak(zaakDto.getOvertredingsdatum(), zaakDto.getPleeglocatie(), persoon.get(), feiten);
		List<ZaakStatus> zaakStatussen = new ArrayList<>();
		ZaakStatus zaakStatus = new ZaakStatus(LocalDate.now(), new Status(1, "Open"), zaak);
		zaakStatussen.add(zaakStatus);

		zaak.setZaakstatus(zaakStatussen);
		Zaak savedZaak = zaakRepository.saveAndFlush(zaak);

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
	public ZaakDto updateZaakStatus(Integer zaakNr, ZaakAddStatusDto zaakAddStatusDto) {
		List<String> notFoundExceptions = new ArrayList<>();
		Optional<Status> status = statusRepository.findById(zaakAddStatusDto.getStatusNr());
		Optional<Zaak> zaak = zaakRepository.findById(zaakNr);

		if (!zaak.isPresent()) {
			notFoundExceptions.add("ZaakNummer: " + zaakNr + " bestaat niet");
		}

		if (!status.isPresent()) {
			notFoundExceptions.add("StatusNummer: " + zaakAddStatusDto.getStatusNr() + " bestaat niet");
		}

		if (notFoundExceptions.size() > 0) {
			throw new NotFoundException(notFoundExceptions.toString());
		}

		ZaakStatus zaakStatus = new ZaakStatus(0,LocalDate.now(), status.get(), zaak.get());

		List<ZaakStatus> zaakStatussen = zaak.get().getZaakstatus();
		zaakStatussen.add(zaakStatus);
		//zaak.get().setZaakstatus(zaakStatussen);
		zaakRepository.saveAndFlush(zaak.get());

		ZaakDto zaakDto = zaakDtoService.setZaakDto(zaak.get());

		return zaakDto;
	}

	public List<ZaakDto> getZaken() {
		List<Zaak> zaken = zaakRepository.findAll();
		List<ZaakDto> zaakDtos = new ArrayList<>();
		for (Zaak zaak : zaken) {
			zaakDtos.add(zaakDtoService.setZaakDto(zaak));
		}
		return zaakDtos;
	}

	public ZaakDto getZaakById(Integer zaakNr) {
		Optional<Zaak> zaakOpgehaald = zaakRepository.findById(zaakNr);
		zaakOpgehaald.orElseThrow(() -> new NotFoundException("ZaakNummer: " + zaakNr + " bestaat niet"));

		Optional<Zaak> zaak = zaakRepository.findById(zaakNr);

		return zaakDtoService.setZaakDto(zaak.get());
	}

	public List<ZaakDto> getZakenByPersoon(Integer persoonnr) {

		Optional<Persoon> persoon = persoonRepository.findById(persoonnr);
		if (!persoon.isPresent()) {
			throw new NotFoundException("Persoonnr " + persoonnr + " bestaat niet");
		}
		List<Zaak> zaken = zaakRepository.findAllByPersoon(persoon.get());

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
		Optional<Zaak> zaak = zaakRepository.findById(zaakNr);

		if (!zaak.isPresent()) {
			notFoundExceptions.add("zaakNummer: " + zaakNr + " bestaat niet");
		}
		for (ZaakAddFeitDto zaakAddFeitDto : listZaakAddFeitDto) {

			Optional<Feit> feitOpgehaald = feitRepository.findById(zaakAddFeitDto.getFeitNr());

			if (!feitOpgehaald.isPresent()) {
				notFoundExceptions.add("feitNummer: " + zaakAddFeitDto.getFeitNr() + " bestaat niet");
			}

		}
		if (notFoundExceptions.size() > 0) {
			notFoundExceptions.add("geen feit(en) toegevoegd");
			throw new NotFoundException(notFoundExceptions.toString());
		}

		List<Feit> zaakFeiten = zaak.get().getFeiten();
		for (ZaakAddFeitDto zaakAddFeitDto : listZaakAddFeitDto) {
			int feitNrDto = zaakAddFeitDto.getFeitNr();
			for (Feit feit : zaakFeiten) {
				if (feit.getFeitnr() == feitNrDto) {
					uniekVeldExceptions.add("feitNummer: " + zaakAddFeitDto.getFeitNr() + " is reeds toegevoegd aan deze zaak");
				}
			}
		}
		if (uniekVeldExceptions.size() > 0) {
			uniekVeldExceptions.add("geen feit(en) toegevoegd");
			throw new UniekVeldException(uniekVeldExceptions.toString());
		}
		for (ZaakAddFeitDto zaakAddFeitDto : listZaakAddFeitDto) {
			Optional<Feit> feitOpgehaald = feitRepository.findById(zaakAddFeitDto.getFeitNr());
			zaakFeiten.add(feitOpgehaald.get());
			zaak.get().setFeiten(zaakFeiten);
		}
		ZaakDto zaakDto = zaakDtoService.setZaakDto(zaak.get());
		return zaakDto;
	}
}
