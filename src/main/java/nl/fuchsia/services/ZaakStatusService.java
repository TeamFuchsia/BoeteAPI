package nl.fuchsia.services;

import nl.fuchsia.model.ZaakStatus;
import nl.fuchsia.repository.ZaakStatusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ZaakStatusService {

    private ZaakStatusRepository zaakStatusRepository;

    public ZaakStatusService(ZaakStatusRepository zaakStatusRepository) {
        this.zaakStatusRepository = zaakStatusRepository;
    }

    public ZaakStatus addZaakStatus(ZaakStatus zaakStatus) {
        zaakStatus.setVeranderdatum(LocalDate.now());

        return zaakStatusRepository.addZaakStatus(zaakStatus);
    }

    public List<ZaakStatus> getZaakStatussen() {
        return zaakStatusRepository.getZaakStatussen();
    }
}
