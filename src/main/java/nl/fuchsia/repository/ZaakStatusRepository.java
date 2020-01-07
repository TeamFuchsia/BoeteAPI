package nl.fuchsia.repository;


import java.util.List;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.model.ZaakStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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

    private static final String GET_ZAAKSTATUSSEN = "SELECT zaakstatus FROM Zaakstatus zaakstatus ";

    /**
     * Haalt een lijst van alle personen uit de database m.b.v. ornm.
     *
     * @return Lijst van personen.
     */
    @Transactional
    public List<ZaakStatus> getZaakStatussen() {
        TypedQuery<ZaakStatus> getAllStatussen = entityManager.createQuery(GET_ZAAKSTATUSSEN, ZaakStatus.class);
        return getAllStatussen.getResultList();
    }
}