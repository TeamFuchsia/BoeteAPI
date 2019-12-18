package nl.fuchsia.services;

import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.Feit;
import nl.fuchsia.repository.FeitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;

@Service
public class FeitService {
    private FeitRepository feitRepository;

    public FeitService(FeitRepository feitRepository) {
        this.feitRepository = feitRepository;
    }

    public Feit addFeit(Feit feit) {
        try {
           return feitRepository.addFeit(feit);
        }
        //Vangt opgevoerde feiten met feitcodes die al in de database voor komt.
        catch (TransactionSystemException e) {
            throw new UniekVeldException("Feitcode: " + feit.getFeitcode() + " bestaat al in de database.");
        }
    }

    public List<Feit> getFeiten() {
        return feitRepository.getFeiten();
    }
}
