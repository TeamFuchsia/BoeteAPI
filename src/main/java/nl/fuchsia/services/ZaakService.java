package nl.fuchsia.services;

import nl.fuchsia.model.Zaak;
import nl.fuchsia.repository.ZaakReposistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZaakService {

    private ZaakReposistory zaakReposistory;

    public ZaakService(ZaakReposistory zaakReposistory) {
        this.zaakReposistory = zaakReposistory;
    }

    public void addZaak(Zaak newZaak) {
        zaakReposistory.addZaak(newZaak);
    }

    // Is niet nodig voor Story, toegevoegd om te kijken of zaak daadwerkelijk in List is geplaatst.
    public List<Zaak> getZaken() {
        return zaakReposistory.getZaken();
    }
}
