package nl.fuchsia.repository;

import nl.fuchsia.model.Feit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeitRepository extends JpaRepository<Feit,Integer> {
}
