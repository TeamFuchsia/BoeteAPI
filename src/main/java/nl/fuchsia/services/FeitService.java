package nl.fuchsia.services;

import nl.fuchsia.model.Feit;
import nl.fuchsia.repository.FeitRepository;
import nl.fuchsia.repository.JDBCFeitRepository;
import nl.fuchsia.repository.ORMFeitRepository;
import org.springframework.stereotype.Service;

@Service
public class FeitService {
    private ORMFeitRepository ormFeitRepository;

    public FeitService(ORMFeitRepository ormFeitRepository) {
        this.ormFeitRepository = ormFeitRepository;
    }

    public void addFeit(Feit feit) {
        ormFeitRepository.addFeit(feit);
    }
}
