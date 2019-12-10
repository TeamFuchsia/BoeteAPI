package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrmPersoonRepository {

    @PersistenceContext(unitName = "entityManagerFactory")  //unitName verwijst naar de naam van de bean in DatabaseConfig.java, entityManagerFactory.
    private EntityManager entityManager;

    /**
     * Haalt een lijst van alle personen uit de database m.b.v. ornm.
     *
     * @return Lijst van personen.
     */
    @Transactional
    public List<Persoon> getOrmPersonen() {
        TypedQuery<Persoon> getAllPersonen = entityManager.createQuery("SELECT a FROM Persoon a", Persoon.class);
        return getAllPersonen.getResultList();
    }

    @Transactional
    public Persoon getPersoonById(Integer persoonnr) {
        return entityManager.find(Persoon.class, persoonnr);
    }

    @Transactional
    public void addPersoon(Persoon persoon) {
        entityManager.persist(persoon);
    }
}
