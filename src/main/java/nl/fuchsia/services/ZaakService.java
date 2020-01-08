package nl.fuchsia.services;

import nl.fuchsia.dto.ZaakAddDto;
import nl.fuchsia.dto.ZaakAddFeitDto;
import nl.fuchsia.exceptionhandlers.NotFoundException;
import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.Feit;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.model.Zaak;
import nl.fuchsia.repository.FeitRepository;
import nl.fuchsia.repository.PersoonRepository;
import nl.fuchsia.repository.ZaakRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZaakService {

	private ZaakRepository zaakRepository;
	private PersoonRepository persoonRepository;
	private FeitRepository feitRepository;

	public ZaakService(ZaakRepository zaakRepository, PersoonRepository persoonRepository, FeitRepository feitRepository) {
		this.zaakRepository = zaakRepository;
		this.persoonRepository = persoonRepository;
		this.feitRepository = feitRepository;
	}

	/**
	 * Voegt een nieuwe zaak toe inclusief een persoon en minimaal 1 feit.
	 *
	 * @param zaakAddDto de ingevoerde zaakDto
	 * @return de gemaakte zaak inclusief persoon en feit(en).
	 */
	public Zaak addZaak(ZaakAddDto zaakAddDto) {
		Zaak zaak = new Zaak();
		List<String> exceptions = new ArrayList<>();

		Persoon persoon = persoonRepository.getPersoonById(zaakAddDto.getPersoonnr());

		if (persoon == null) {
			exceptions.add(" Persoonnr " + zaakAddDto.getPersoonnr() + " bestaat niet");
		}
		List<Feit> feiten = new ArrayList<>();
		for (int feitNr : zaakAddDto.getFeitnrs()) {
			Feit feit = feitRepository.getFeitById(feitNr);
			if (feit == null) {
				exceptions.add("Feitnr " + feitNr + " bestaat niet");
			} else feiten.add(feit);
		}
		if (exceptions.size() > 0) {
			throw new NotFoundException(exceptions.toString());
		}
		zaak.setOvertredingsdatum(zaakAddDto.getOvertredingsdatum());
		zaak.setPleeglocatie(zaakAddDto.getPleeglocatie());
		zaak.setPersoon(persoon);
		zaak.setFeiten(feiten);
		return zaakRepository.addZaak(zaak);
	}

	public List<Zaak> getZaken() {
		return zaakRepository.getZaken();
	}

	public Zaak getZaakById(Integer zaakNr) {
		return zaakRepository.getZaakById(zaakNr);
	}

	public List<Zaak> getZakenByPersoon(Integer persoonnr) {

		Persoon persoon = persoonRepository.getPersoonById(persoonnr);

		if (persoon == null) {
			throw new NotFoundException("Persoonnr " + persoonnr + " bestaat niet");
		}

		return zaakRepository.getZakenByPersoon(persoon);
	}

	@Transactional
	public Zaak updZaakFeit(Integer zaakNr, ZaakAddFeitDto zaakAddFeitDto) {
		List<String> exceptions = new ArrayList<>();

		if (zaakRepository.getZaakById(zaakNr) == null) {
			exceptions.add("ZaakNummer: " + zaakNr + " bestaat niet!");
		}
		if (feitRepository.getFeitById(zaakAddFeitDto.getFeitNr()) == null) {
			exceptions.add("FeitNummer: " + zaakAddFeitDto.getFeitNr() + " bestaat niet!");
		}
		if (exceptions.size() > 0) {
			throw new NotFoundException(exceptions.toString());
		}

		int feitNrDto = zaakAddFeitDto.getFeitNr();
		Zaak zaak = zaakRepository.getZaakById(zaakNr);
		Feit feitDto = feitRepository.getFeitById(feitNrDto);

		List<Feit> feiten = zaak.getFeiten();
		for (Feit feit : feiten) {
			if (feit.getFeitNr() == feitNrDto) {
				throw new UniekVeldException("FeitNummer: " + zaakAddFeitDto.getFeitNr() + " is reeds toegevoegd aan deze zaak.");
			}
		}
		feiten.add(feitDto);
		zaak.setFeiten(feiten);
		return zaak;
	}
}
