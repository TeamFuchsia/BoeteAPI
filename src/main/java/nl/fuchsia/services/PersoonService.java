package nl.fuchsia.services;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.repository.PersoonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersoonService {

    private PersoonRepository persoonRepository;

    @Autowired
    public PersoonService(PersoonRepository persoonRepository) {
        this.persoonRepository = persoonRepository;
    }

    public List<Persoon> getPersonen() {
        return persoonRepository.getAllePersonen();
    }

    public void addPersoon(Persoon persoon) {
        persoonRepository.addPersoonById(persoon);
    }
}
