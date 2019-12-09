package nl.fuchsia.services;

import nl.fuchsia.model.Feit;
import nl.fuchsia.repository.FeitRepository;
import nl.fuchsia.repository.JDBCFeitRepository;
import org.springframework.stereotype.Service;

@Service
public class FeitService {
    private JDBCFeitRepository jdbcFeitRepository;


    public FeitService(JDBCFeitRepository jdbcFeitRepository) {
        this.jdbcFeitRepository = jdbcFeitRepository;
    }

    public void addFeit(Feit feit) {
        jdbcFeitRepository.addFeit(feit);
    }
}
