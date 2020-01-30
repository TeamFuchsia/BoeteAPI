package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.model.Zaak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZaakRepository extends JpaRepository <Zaak,Integer>{

	@Query(value = "FROM Zaak zaak WHERE zaak.persoon = :nummer")
	List<Zaak> getAllZakenByPersoon(@Param("nummer") Persoon persoon);
}
