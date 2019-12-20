package nl.fuchsia.services;

import nl.fuchsia.dto.ZaakDto;
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

	public Zaak addZaak(ZaakDto zaakDto) {

		Zaak zaak = new Zaak();
		String errorMessage = "";

		//todo Zorg ervoor dat alle exception getoond worden, dus beide (persoonnr en feitnr) bestaan niet.
		try {
			Persoon persoon = persoonRepository.getPersoonById(zaakDto.getPersoonnr());

			if (persoon == null) {
				errorMessage = "Persoonnr " + zaakDto.getPersoonnr() + " bestaat niet";
				throw new NullPointerException();
			}
			List<Feit> feiten = new ArrayList<>();
			for (int feitNr : zaakDto.getFeitnrs()) {
				Feit feit = feitRepository.getFeitById(feitNr);
				if (feit == null) {
					errorMessage = "Feitnr " + feitNr + " bestaat niet";
					throw new NullPointerException();
				}
				feiten.add(feit);
			}
			zaak.setOvertredingsdatum(zaakDto.getOvertredingsdatum());
			zaak.setPleeglocatie(zaakDto.getPleeglocatie());
			zaak.setPersoon(persoon);
			zaak.setFeiten(feiten);
		} catch (NullPointerException e) {
			throw new NullException(errorMessage);
		}
		return zaakRepository.addZaak(zaak);
	}

	public List<Zaak> getZaken() {
		return zaakRepository.getZaken();
	}

	public Zaak getZaakById(Integer zaakNr) {
		return zaakRepository.getZaakById(zaakNr);
	}
}
