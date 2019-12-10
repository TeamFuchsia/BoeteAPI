package nl.fuchsia.services;

import nl.fuchsia.repository.JdbcPersoonRepository;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.repository.OrmPersoonRepository;
import nl.fuchsia.repository.PersoonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersoonService {

    private PersoonRepository persoonRepository;
    private JdbcPersoonRepository jdbcPersoonRepository;
    private OrmPersoonRepository ormPersoonRepository;

    @Autowired
    public PersoonService(PersoonRepository persoonRepository, JdbcPersoonRepository jdbcPersoonRepository, OrmPersoonRepository ormPersoonRepository) {
        this.persoonRepository = persoonRepository;
        this.jdbcPersoonRepository = jdbcPersoonRepository;
        this.ormPersoonRepository = ormPersoonRepository;
    }

    /**
     * Roept de lijst van personen op via de persoonRepository.
     *
     * @return - Roept de methode getPersonen aan in persoonRepository.
     */
    //  Hoort niet in de user story 1-RH.
    public List<Persoon> getPersonen() {
        return persoonRepository.getPersonen();
    }

    /**
     * Geeft een lijst van personen die in de database staan via de jdbcPersoonRepository.
     *
     * @return - Roept de methode getJdbcPersonen aan in jdbcPersoonRepository.
     */
    public List<Persoon> getJdbcPersonen() {
        return jdbcPersoonRepository.getJdbcPersonen();
    }

    public List<Persoon> getOrmPersonen(){
        return ormPersoonRepository.getOrmPersonen();
    }

    /**
     * Voegt de persoon toe via de persoonRepository.
     *
     * @param persoon - De toe te voegen persoon.
     */
    public void addPersoon(Persoon persoon) {
        ormPersoonRepository.addPersoon(persoon);
    }

    /**
     * haalt de persoon per ID (persoonnr) via de OrmPersoonRepository.
     *
     * @param persoonnr - ID de op te halen persoon.
     */
    public Persoon getPersoonById(Integer persoonnr) {
        return ormPersoonRepository.getPersoonById(persoonnr);
    }
}