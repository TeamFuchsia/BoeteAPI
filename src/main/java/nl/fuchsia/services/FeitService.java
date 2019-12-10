package nl.fuchsia.services;

import nl.fuchsia.model.Feit;
import nl.fuchsia.repository.JDBCFeitRepository;
import nl.fuchsia.repository.ORMFeitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeitService {
    private ORMFeitRepository ormFeitRepository;
    private JDBCFeitRepository jdbcFeitRepository;

    public FeitService(ORMFeitRepository ormFeitRepository, JDBCFeitRepository jdbcFeitRepository) {
        this.ormFeitRepository = ormFeitRepository;
        this.jdbcFeitRepository = jdbcFeitRepository;
    }

    public void addFeit(Feit feit) {
        ormFeitRepository.addFeit(feit);
    }

    public List<Feit> getFeiten(){
       return jdbcFeitRepository.getFeiten();
    }
}
