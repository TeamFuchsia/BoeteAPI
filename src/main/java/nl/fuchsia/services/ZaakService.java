package nl.fuchsia.services;

import nl.fuchsia.dto.ZaakAddDto;
import nl.fuchsia.exceptionhandlers.NullException;
import nl.fuchsia.model.Feit;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.model.Zaak;
import nl.fuchsia.repository.FeitRepository;
import nl.fuchsia.repository.PersoonRepository;
import nl.fuchsia.repository.ZaakRepository;
import org.springframework.stereotype.Service;

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

		//todo Zorg ervoor dat alle exception getoond worden, dus beide (persoonnr en feitnr) bestaan niet.
			Persoon persoon = persoonRepository.getPersoonById(zaakAddDto.getPersoonnr());

			if (persoon == null) {
				throw new NullException("Persoonnr " + zaakAddDto.getPersoonnr() + " bestaat niet");
			}
			List<Feit> feiten = new ArrayList<>();
			for (int feitNr : zaakAddDto.getFeitnrs()) {
				Feit feit = feitRepository.getFeitById(feitNr);
				if (feit == null) {
					throw new NullException("Feitnr " + feitNr + " bestaat niet");
				}
				feiten.add(feit);
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

	public List<Zaak>  getZakenByPersoon(Integer persoonnr) {

		Persoon persoon = persoonRepository.getPersoonById(persoonnr);

		if (persoon == null) {
			throw new NullException("Persoonnr " + persoonnr + " bestaat niet");
		}

		return zaakRepository.getZakenByPersoon(persoon);
	}
}