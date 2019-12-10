package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class OrmPersoonRepository {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    @Transactional
    public Persoon getPersoonById(Integer persoonnr) {
        return em.find(Persoon.class, persoonnr);
    }

    @Transactional
    public void addPersoon(Persoon persoon) {
        em.persist(persoon);
    }
}