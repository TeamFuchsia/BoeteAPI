package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public interface PersoonRepository extends JpaRepository<Persoon,Integer> {

}

