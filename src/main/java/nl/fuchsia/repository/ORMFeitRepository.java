package nl.fuchsia.repository;

import nl.fuchsia.model.Feit;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class ORMFeitRepository {

    @PersistenceContext(unitName = "Database_BoeteAPI")
    private EntityManager em;


    @Transactional
    public void addFeit(Feit feit){
        em.persist(feit);
    }


}
