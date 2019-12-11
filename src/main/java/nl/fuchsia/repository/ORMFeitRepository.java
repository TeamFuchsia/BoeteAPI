package nl.fuchsia.repository;

import nl.fuchsia.model.Feit;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ORMFeitRepository {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    /**
     * Voegt een Feit toe aan de database
     * @param feit is het toe te voegen feit.
     */
    @Transactional
    public void addFeit(Feit feit) {
        entityManager.persist(feit);
    }

    /**
     * Haalt alle Feiten uit de database
     * @return een lijst met alle feiten
     */
    @Transactional
    public List<Feit> getFeiten() {
        TypedQuery<Feit> getAllFeiten = entityManager.createQuery("SELECT feit FROM Feit feit", Feit.class);
        return getAllFeiten.getResultList();
    }
}