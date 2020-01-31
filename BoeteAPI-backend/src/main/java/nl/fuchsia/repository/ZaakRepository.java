package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.model.Zaak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZaakRepository extends JpaRepository<Zaak, Integer> {

	List<Zaak> findAllByPersoon(Persoon persoon);
}
