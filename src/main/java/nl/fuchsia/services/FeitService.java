package nl.fuchsia.services;

import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.Feit;
import nl.fuchsia.repository.JDBCFeitRepository;
import nl.fuchsia.repository.ORMFeitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;

@Service
public class FeitService {
    private ORMFeitRepository ormFeitRepository;

    public FeitService(ORMFeitRepository ormFeitRepository) {
        this.ormFeitRepository = ormFeitRepository;
    }

    public Feit addFeit(Feit feit) {
        try {
           return ormFeitRepository.addFeit(feit);
        }
        //Vangt opgevoerde feiten met feitcodes die al in de database voor komt.
        catch (TransactionSystemException e) {
            throw new UniekVeldException("Feitcode: " + feit.getFeitcode() + " bestaat al in de database.");
        }
    }

    public List<Feit> getFeiten() {
        return ormFeitRepository.getFeiten();
    }
}