package nl.fuchsia.services;

import nl.fuchsia.model.Feit;
import nl.fuchsia.repository.FeitRepository;
import org.springframework.stereotype.Service;

@Service
public class FeitService {

    private FeitRepository feitRepository;

    public FeitService(FeitRepository feitRepository) {
        this.feitRepository = feitRepository;
    }

    public void addFeit(Feit feit) {
        feitRepository.addFeit(feit);
    }

    public Long getNieuwFeitnummer() {
        return feitRepository.getNieuwFeitnummer();
    }
}
