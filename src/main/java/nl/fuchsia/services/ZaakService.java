package nl.fuchsia.services;

import nl.fuchsia.model.Zaak;
import nl.fuchsia.repository.OrmZaakRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZaakService {

    private OrmZaakRepository ormZaakRepository;

    public ZaakService(OrmZaakRepository ormZaakRepository) {
        this.ormZaakRepository = ormZaakRepository;
    }

    public void addZaak(Zaak zaak) {
        ormZaakRepository.addZaak(zaak);
    }

    public List<Zaak> getZaken() {
        return ormZaakRepository.getZaken();
    }


}