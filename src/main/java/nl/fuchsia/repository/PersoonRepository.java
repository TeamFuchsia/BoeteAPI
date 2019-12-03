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

    /**
     * Vult de personen repository met 1 record bij constructie.
     */
    public PersoonRepository() {
        personen.add(new Persoon(1, "Rense1", "Houwing", "Voltawerk", "36", "8401 EN", "Gorredijk", "123456789", LocalDate.of(1967, 10, 12)));
    }

    public List<Persoon> getPersonen() {
        return personen;
    }

    /**
     * Voegt de persoon toe aan de personen repository
     *
     * @param persoon - de toe te voegen persoon.
     */
    // persoonId wordt automatisch gegenereerd.
    public void addPersoon(Persoon persoon) {
        Persoon klantMaxId = Collections.max(personen, Comparator.comparing(Persoon::getPersoonId));
        Integer newId = klantMaxId.getPersoonId() + 1;
        persoon.setPersoonId(newId);
        personen.add(persoon);
    }
}
