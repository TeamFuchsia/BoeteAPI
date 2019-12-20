package nl.fuchsia.services;

import nl.fuchsia.dto.PersoonEditDto;
import nl.fuchsia.exceptionhandlers.NullException;
import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.repository.PersoonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;

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

        return persoonRepository.getPersoonById(persoonnr);

    }

    /**
     * wijzigd de persoon in de database op bassis van de meegeven ID nummer in de persoonDto.
     * @param persoonDto zijn de gegevens waarin de persoon gewijzigd moet worden
     * @return de nieuwe persoon in de database
     */
    public Persoon updatePersoonById(PersoonEditDto persoonDto) {

        try {
            if (persoonRepository.getPersoonById(persoonDto.getPersoonnr()) == null) {
                throw new NullException("Persoonnummer: " + persoonDto.getPersoonnr() + " bestaat niet!");
            }
        } catch (TransactionSystemException e) {
            throw new UniekVeldException("BSN nummer: " + persoonDto.getBsn() + " bestaat reeds.");
        }
        Persoon persoon = new Persoon(persoonDto.getPersoonnr(),
                                      persoonDto.getVoornaam(),
                                      persoonDto.getAchternaam(),
                                      persoonDto.getStraat(),
                                      persoonDto.getHuisnummer(),
                                      persoonDto.getPostcode(),
                                      persoonDto.getWoonplaats(),
                                      persoonDto.getBsn(),
                                      persoonDto.getGeboortedatum());

        return persoonRepository.updatePersoonById(persoon);
    }
}