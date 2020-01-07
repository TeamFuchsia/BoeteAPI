package nl.fuchsia.repository;


import nl.fuchsia.model.ZaakStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ZaakStatusRepository {

    /**
     * maakt een {@link EntityManager} t.b.v. de {@link PersistenceContext}.
     */
    //unitName verwijst naar de naam van de bean in DatabaseConfig.java, entityManagerFactory.
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Transactional
    public ZaakStatus addZaakStatus(ZaakStatus zaakStatus) {
        entityManager.persist(zaakStatus);
        return zaakStatus;
    }
}