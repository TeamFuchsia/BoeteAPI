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
        if (!personen.isEmpty()) {
            persoon.setPersoonId(Collections.max(personen, Comparator.comparing(Persoon::getPersoonId))
                    .getPersoonId() + 1);
            personen.add(persoon);
        }else{
            persoon.setPersoonId(1);
            personen.add(persoon);
        }
    }
}
