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

		try {
			Persoon persoon = persoonRepository.getPersoonById(zaakDto.getPersoonnr());
			if (persoon.equals(null)) {
				String bericht = "Persoon";
				throw new NullPointerException(bericht);
			}
			List<Feit> feiten = new ArrayList<>();
			for (int feitNr : zaakDto.getFeitnrs()) {
				Feit feit = feitRepository.getFeitById(feitNr);
				if (feit.equals(null)) {
					String bericht = "Feit";
					throw new NullPointerException(bericht);
				}
				feiten.add(feit);
			}
			zaak.setOvertredingsDatum(zaakDto.getOvertredingsDatum());
			zaak.setPleegLocatie(zaakDto.getPleegLocatie());
			zaak.setPersoon(persoon);
			zaak.setFeiten(feiten);
		} catch (NullPointerException e) {

			//throw new NullException();
			throw new NullException("Persoonnr: " + zaakDto.getPersoonnr() + " bestaat niet.");
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
