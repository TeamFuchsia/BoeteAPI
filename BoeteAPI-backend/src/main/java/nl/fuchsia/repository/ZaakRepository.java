package nl.fuchsia.repository;

import nl.fuchsia.model.Zaak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZaakRepository extends JpaRepository <Zaak,Integer>{

}
