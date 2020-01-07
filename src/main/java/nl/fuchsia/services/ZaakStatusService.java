package nl.fuchsia.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.model.ZaakStatus;
import nl.fuchsia.repository.ZaakStatusRepository;
import org.springframework.stereotype.Service;

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
