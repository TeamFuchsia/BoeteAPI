package nl.fuchsia.services;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.repository.JdbcPersoonRepository;
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

//    Hoort niet in de user story 1-RH.
//    public List<Persoon> getPersonen() {
//        return persoonRepository.getPersonen();}
//
//    onderdstaande is t.b.v. toevoegen persoon middels Jdbc
//    public void addPersoon(Persoon persoon) {
//        jdbcPersoonRepository.addPersoon(persoon);}

    /**
     * Voegt de persoon toe via de OrmPersoonRepository.
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