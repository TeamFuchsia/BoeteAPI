package nl.fuchsia.repository;


import java.util.List;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.model.Zaak;
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

    private static final String UPDATE_DATUM_ZAAKSTATUS = "UPDATE zaakstatus e SET e.veranderdatum = '09-01-2020'"+
            "WHERE e.zaakstatusnr = 5";

    //"UPDATE Employee e SET e.salary = e.salary + :increment "
    //              + "WHERE e.dept = :dept");

    @Transactional
    public ZaakStatus addZaakStatus(ZaakStatus zaakStatus) {
        entityManager.persist(zaakStatus);
        return zaakStatus;
    }
    @Transactional
    public List<ZaakStatus> updateDatum() {
        TypedQuery<ZaakStatus> updateDatum = entityManager.createQuery(UPDATE_DATUM_ZAAKSTATUS, ZaakStatus.class);
        return updateDatum.getResultList();
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