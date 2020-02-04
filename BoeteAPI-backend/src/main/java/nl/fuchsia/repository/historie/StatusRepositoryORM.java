package nl.fuchsia.repository.historie;

import nl.fuchsia.model.Status;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class StatusRepositoryORM {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    /**
     * Haalt de status op basis van het statusnr.
     *
     * @param statusnr van de op te halen status.
     * @return het opgehaalde feit.
     */
    @Transactional
    public Status getStatusById(Integer statusnr) {
        return entityManager.find(Status.class, statusnr);
    }
}
