package nl.fuchsia.repository;

import nl.fuchsia.model.Zaak;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import java.util.List;

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
}
