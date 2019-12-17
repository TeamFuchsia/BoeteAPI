package nl.fuchsia.services;

import nl.fuchsia.dto.ZaakDto;
import nl.fuchsia.exceptionhandlers.NullException;
import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.model.Zaak;
import nl.fuchsia.repository.PersoonRepository;
import nl.fuchsia.repository.ZaakRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;

@Service
public class ZaakService {

    private ZaakRepository zaakRepository;
    private PersoonRepository persoonRepository;

    public ZaakService(ZaakRepository zaakRepository, PersoonRepository persoonRepository) {
        this.zaakRepository = zaakRepository;
        this.persoonRepository = persoonRepository;
    }

    public Zaak addZaak(ZaakDto zaakDto) {

        Zaak zaak = null;
        try {
            Persoon persoon = persoonRepository.getPersoonById(zaakDto.getPersoonnr());
            if (persoon.equals(null)){
                throw new NullPointerException();
            }
            zaak = new Zaak();
            zaak.setOvertredingsDatum(zaakDto.getOvertredingsDatum());
            zaak.setPleegLocatie(zaakDto.getPleegLocatie());
            zaak.setPersoon(persoon);
        } catch (NullPointerException e) {
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