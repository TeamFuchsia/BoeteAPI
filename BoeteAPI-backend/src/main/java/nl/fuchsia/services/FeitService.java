package nl.fuchsia.services;

import nl.fuchsia.exceptionhandlers.NotFoundException;
import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.Feit;
import nl.fuchsia.repository.FeitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;
import java.util.Optional;

@Service
public class FeitService {
	private FeitRepository feitRepository;

	@Autowired
	public FeitService(FeitRepository feitRepository) {
		this.feitRepository = feitRepository;
	}

	public Feit addFeit(Feit feit) {
		try {
			return feitRepository.save(feit);
		}
		//Vangt opgevoerde feiten met feitcodes die al in de database voor komt.
		catch (TransactionSystemException e) {
			throw new UniekVeldException("Feitcode: " + feit.getFeitcode() + " bestaat al in de database.");
		}
	}

	public List<Feit> getFeiten() {
		return feitRepository.findAll();
	}

	public Feit updateFeitById(Feit feit) {

		try {
			Optional<Feit> feitOpgehaald = feitRepository.findById(feit.getFeitnr());

			feitOpgehaald.orElseThrow(() -> new NotFoundException("Feitnummer: " + feit.getFeitnr() + " bestaat niet!"));

			if (!(feitOpgehaald.get().getFeitcode().equals(feit.getFeitcode()))) {
				throw new UniekVeldException("Feitcode: " + feitOpgehaald.get().getFeitcode() + " mag niet gewijzigd worden in " + feit.getFeitcode());
			}

			feitRepository.save(feit);

		} catch (TransactionSystemException e) {
			throw new UniekVeldException("Feitcode: " + feit.getFeitcode() + " bestaat reeds.");
		}

		return feit;
	}
}
