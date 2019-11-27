package nl.fuchsia.repository;


import nl.fuchsia.model.Persoon;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
public class PersoonRepository {

    private List<Persoon> personen = new ArrayList<>();

    public PersoonRepository() {
        personen.add(new Persoon(1, "Rense", "Houwing", "Voltawerk", "36", "8401 EN", "Gorredijk", "bsn-3-4-5", LocalDate.of(1967, 10, 12)));
    }

    public List<Persoon> getAllePersonen() {
        return personen;
    }


    public void addPersoonById(Persoon persoon){
        Persoon klantMaxId = Collections.max(personen, Comparator.comparing(Persoon::getPersoonId));
        Integer newId = klantMaxId.getPersoonId() + 1;
        persoon.setPersoonId(newId);
        personen.add(persoon);
    }
}
