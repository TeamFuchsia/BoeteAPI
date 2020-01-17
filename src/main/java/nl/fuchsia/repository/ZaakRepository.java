package nl.fuchsia.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.model.Zaak;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ZaakRepository {

    private static final String GET_ZAKEN = "SELECT zaak FROM Zaak zaak ";
    private static final String GET_ZAKEN_BY_PERSOON = GET_ZAKEN + "where zaak.persoon=:persoon";

    /**
     * maakt een {@link EntityManager} t.b.v. de {@link PersistenceContext}.
     */
    //unitName verwijst naar de naam van de bean in DatabaseConfig.java, entityManagerFactory.
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Transactional
    public Zaak addZaak(Zaak zaak) {
        entityManager.persist(zaak);
        entityManager.flush();
        return zaak;
    }

    @Transactional
    public List<Zaak> getZaken() {
        TypedQuery<Zaak> getAllZaken = entityManager.createQuery(GET_ZAKEN, Zaak.class);
        return getAllZaken.getResultList();
    }

    @Transactional
    public Zaak getZaakById(Integer zaakNr) {
        return entityManager.find(Zaak.class, zaakNr);
    }

    @Transactional
    public List<Zaak> getZakenByPersoon(Persoon persoon) {

        TypedQuery<Zaak> zakenByPersoon = entityManager.createQuery(GET_ZAKEN_BY_PERSOON, Zaak.class);
        zakenByPersoon.setParameter("persoon", persoon);
        return zakenByPersoon.getResultList();
    }
}
