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
     * wijzigd de persoon in de database op bassis van de meegeven ID nummer in de persoonEditDto.
     * @param persoonEditDto zijn de gegevens waarin de persoon gewijzigd moet worden
     * @return de nieuwe persoon in de database
     */
    public Persoon updatePersoonById(PersoonEditDto persoonEditDto) {

        Persoon persoon;

        try {
            if (persoonRepository.getPersoonById(persoonEditDto.getPersoonnr()) == null) {
                throw new NullException("Persoonnummer: " + persoonEditDto.getPersoonnr() + " bestaat niet!");
            }
                persoon = new Persoon(persoonEditDto.getPersoonnr(),
                persoonEditDto.getVoornaam(),
                persoonEditDto.getAchternaam(),
                persoonEditDto.getStraat(),
                persoonEditDto.getHuisnummer(),
                persoonEditDto.getPostcode(),
                persoonEditDto.getWoonplaats(),
                persoonEditDto.getBsn(),
                persoonEditDto.getGeboortedatum());

            persoonRepository.updatePersoonById(persoon);

        } catch (TransactionSystemException e) {
            throw new UniekVeldException("BSN nummer: " + persoonEditDto.getBsn() + " bestaat reeds.");
        }

        return persoon;
    }
}