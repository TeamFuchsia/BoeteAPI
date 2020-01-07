package nl.fuchsia.repository;

import nl.fuchsia.model.Zaak;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ListZaakReposistory {

    private List<Zaak> zaakList = new ArrayList<>();
    private int nieuweZaakNr = 1;

    public void addZaak(Zaak zaak) {
        zaak.setZaaknr(nieuweZaakNr);
        nieuweZaakNr += 1;
        zaakList.add(zaak);
    }

    public List<Zaak> getZaken() {
        return zaakList;
    }
}
