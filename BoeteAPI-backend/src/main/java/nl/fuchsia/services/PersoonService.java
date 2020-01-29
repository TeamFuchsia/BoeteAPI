package nl.fuchsia.services;

import java.util.List;
import java.util.Optional;

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
        return persoonRepository.findAll();
    }

    /**
     * Voegt de persoon toe via de persoonRepository.
     *
     * @param persoon - De toe te voegen persoon.
     */
    public Persoon addPersoon(Persoon persoon) {
        try {
            return persoonRepository.save(persoon);
        } catch (TransactionSystemException e) {
            throw new UniekVeldException("BSN nummer: " + persoon.getBsn() + " bestaat reeds.");
        }
    }

    /**
     * haalt de persoon per ID (persoonnr) via de OrmPersoonRepository.
     *
	 * @param persoonnr - ID de op te halen persoon.
	 * @return
	 */
    public Persoon getPersoonById(Integer persoonnr) {
        Optional<Persoon> persoon = persoonRepository.findById(persoonnr);

        return persoon.orElseThrow(() -> new NotFoundException("PersoonNummer: " + persoonnr + " bestaat niet"));
    }

    /**
     * wijzigd de persoon in de database op bassis van de meegeven ID nummer in de persoon.
     *
     * @param persoon zijn de gegevens waarin de persoon gewijzigd moet worden
     * @return de nieuwe persoon in de database
     */
    public Persoon updatePersoonById(Integer persoonnr, Persoon persoon) {
        try {
			Optional<Persoon> persoonOpgehaald = persoonRepository.findById(persoonnr);
			persoonOpgehaald.orElseThrow(() -> new NotFoundException("PersoonNummer: " + persoonnr + " bestaat niet"));
			persoon.setPersoonnr(persoonnr);
            persoonRepository.save(persoon);

        } catch (TransactionSystemException e) {
            throw new UniekVeldException("BSN nummer: " + persoon.getBsn() + " bestaat reeds.");
        }

        return persoon;
    }
}
