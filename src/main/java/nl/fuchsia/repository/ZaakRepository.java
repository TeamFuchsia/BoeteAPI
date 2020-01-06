package nl.fuchsia.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.fuchsia.model.Payload;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.model.Zaak;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ZaakRepository {

    /**
     * maakt een {@link EntityManager} t.b.v. de {@link PersistenceContext}.
     */
    //unitName verwijst naar de naam van de bean in DatabaseConfig.java, entityManagerFactory.
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Transactional
    public Zaak addZaak(Zaak zaak) {
        entityManager.persist(zaak);
        return zaak;
    }

    @Transactional
    public List<Zaak> getZaken() {
        TypedQuery<Zaak> getAllZaken = entityManager.createQuery("SELECT zaak FROM Zaak zaak ", Zaak.class);
        return getAllZaken.getResultList();
    }

    @Transactional
    public Zaak getZaakById(Integer zaakNr) {
        return entityManager.find(Zaak.class, zaakNr);
    }

    @Transactional
    public List<Zaak> getZakenByPersoon(Persoon persoon) {

        TypedQuery<Zaak> zakenByPersoon = entityManager.createQuery("SELECT zaak FROM Zaak zaak where zaak.persoon=:persoon", Zaak.class);
        zakenByPersoon.setParameter("persoon", persoon);
        return zakenByPersoon.getResultList();
    }
}