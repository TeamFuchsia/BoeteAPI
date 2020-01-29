package nl.fuchsia.repository;

import nl.fuchsia.model.Feit;
import nl.fuchsia.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status,Integer> {
}
