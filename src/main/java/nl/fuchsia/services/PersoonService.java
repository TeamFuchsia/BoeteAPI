package nl.fuchsia.services;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.repository.PersoonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersoonService {

    private PersoonRepository persoonRepository;

    @Autowired
    public PersoonService(PersoonRepository persoonRepository) {
        this.persoonRepository = persoonRepository;
    }

    /**
     * Roept de lijst van personen op via de persoonRepository.
     *
     * @return - Roept de methode getPersonen aan in persoonRepository.
     */
    //  hoort niet in de user story 1-RH.
    public List<Persoon> getPersonen() {
        return persoonRepository.getPersonen();
    }

    /**
     * Voegt de persoon toe via de persoonRepository.
     *
     * @param persoon - De toe te voegen persoon.
     */
    public void addPersoon(Persoon persoon) {
        persoonRepository.addPersoon(persoon);
    }
}
