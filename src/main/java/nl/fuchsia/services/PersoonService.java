package nl.fuchsia.services;

import java.util.List;

import nl.fuchsia.exceptionhandlers.MissingIdExeption;
import nl.fuchsia.exceptionhandlers.NotFoundException;
import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.repository.PersoonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionSystemException;

@Component
public class PersoonService {
    private PersoonRepository persoonRepository;

    @Autowired
    public PersoonService(PersoonRepository persoonRepository) {
        this.persoonRepository = persoonRepository;
    }

    /**
     * Geeft een lijst van personen die in de database staan via de ormPersoonRepository.
     *
     * @return - Roept de methode getOrmPersonen aan in ormPersoonRepository.
     */
    public List<Persoon> getPersonen() {
        return persoonRepository.getPersonen();
    }

    /**
     * Voegt de persoon toe via de persoonRepository.
     *
     * @param persoon - De toe te voegen persoon.
     */
    public Persoon addPersoon(Persoon persoon) {
        try {
            return persoonRepository.addPersoon(persoon);
        } catch (TransactionSystemException e) {
            throw new UniekVeldException("BSN nummer: " + persoon.getBsn() + " bestaat reeds.");
        }
    }

    /**
     * haalt de persoon per ID (persoonnr) via de OrmPersoonRepository.
     *
     * @param persoonnr - ID de op te halen persoon.
     */
    public Persoon getPersoonById(Integer persoonnr) {
Persoon persoon = persoonRepository.getPersoonById(persoonnr);

        if (persoon == null) {
            throw new NotFoundException("PersoonNummer: " + persoonnr + " bestaat niet");
        }

        return persoon;
    }

    /**
     * wijzigd de persoon in de database op bassis van de meegeven ID nummer in de persoon.
     * @param persoon zijn de gegevens waarin de persoon gewijzigd moet worden
     * @return de nieuwe persoon in de database
     */
    public Persoon updatePersoonById(Persoon persoon) {

        try {
            if (persoon.getPersoonnr() == null){
                throw new MissingIdExeption("Geen Persoonnummer ingevoerd");
            }
            if (persoonRepository.getPersoonById(persoon.getPersoonnr()) == null) {
                throw new NotFoundException("Persoonnummer: " + persoon.getPersoonnr() + " bestaat niet!");
            }
            persoonRepository.updatePersoonById(persoon);

        } catch (TransactionSystemException e) {
            throw new UniekVeldException("BSN nummer: " + persoon.getBsn() + " bestaat reeds.");
        }

        return persoon;
    }
}