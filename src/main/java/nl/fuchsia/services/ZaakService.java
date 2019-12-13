package nl.fuchsia.services;

import nl.fuchsia.model.Zaak;
import nl.fuchsia.repository.ZaakRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZaakService {

    private ZaakRepository zaakRepository;

    public ZaakService(ZaakRepository zaakRepository) {
        this.zaakRepository = zaakRepository;
    }

    public Zaak addZaak(Zaak zaak) {
        return zaakRepository.addZaak(zaak);
    }

    public List<Zaak> getZaken() {
        return zaakRepository.getZaken();
    }

    public Zaak getZaakById(Integer zaakNr) {
        return zaakRepository.getZaakById(zaakNr);
    }
}