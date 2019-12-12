package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PersoonRepository {

    /**
     * maakt een {@link EntityManager} t.b.v. de {@link PersistenceContext}.
     */
    //unitName verwijst naar de naam van de bean in DatabaseConfig.java, entityManagerFactory.
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    /**
     * Haalt een lijst van alle personen uit de database m.b.v. ornm.
     *
     * @return Lijst van personen.
     */
    @Transactional
    public List<Persoon> getPersonen() {
        TypedQuery<Persoon> getAllPersonen = entityManager.createQuery("SELECT persoon FROM Persoon persoon ", Persoon.class);
        return getAllPersonen.getResultList();
    }

    /**
     * Haalt de persoon op op basis van het persoonnr.
     *
     * @param persoonnr het persoonnr van de op te halen persoon.
     * @return de opgehaalde persoon.
     */
    @Transactional
    public Persoon getPersoonById(Integer persoonnr) {
        return entityManager.find(Persoon.class, persoonnr);
    }

    /**
     * Voegt een nieuwe persoon toe.
     *
     * @param persoon de toe te voegen persoon.
     */
    @Transactional
    public Persoon addPersoon(Persoon persoon) {
        entityManager.persist(persoon);
        return persoon;
    }
}
