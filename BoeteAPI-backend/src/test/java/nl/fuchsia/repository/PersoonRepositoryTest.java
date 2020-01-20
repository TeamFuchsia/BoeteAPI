package nl.fuchsia.repository;

import nl.fuchsia.configuration.TestDatabaseConfig;
import nl.fuchsia.model.Persoon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDatabaseConfig.class)
@Transactional(propagation = Propagation.REQUIRES_NEW)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersoonRepositoryTest {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Autowired
    private PersoonRepository persoonRepository;

    @BeforeEach
    public void setup() {
        entityManager.createQuery("Delete FROM Persoon").executeUpdate();

        Persoon persoon = new Persoon("Rense1", "Houwing", "De buren", "12", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));

        persoonRepository.addPersoon(persoon);
    }

    @Test
    public void testAddPersoon() {
        persoonRepository.addPersoon(new Persoon("Rense2", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456787", LocalDate.of(1990, 10, 12)));

        assertThat(persoonRepository.getPersonen()).hasSize(2);

        Persoon persoon = persoonRepository.addPersoon(new Persoon("Rense3", "Houwing", "De buren", "10", "8402 GH", "Drachten", "124456788", LocalDate.of(1990, 10, 12)));

        assertThat(persoonRepository.getPersonen()).hasSize(3);
        assertThat(persoonRepository.getPersoonById(persoon.getPersoonnr()).getVoornaam()).isNotEqualTo("Rense1");
    }

    @Test()
    public void testGetPersoonById() {
        Persoon persoon = persoonRepository.addPersoon(new Persoon("Rense2", "Houwing", "De buren", "12", "8402 GH", "Drachten", "123456788", LocalDate.of(1990, 10, 12)));

        assertThat(persoonRepository.getPersonen()).hasSize(2);
        assertThat(persoonRepository.getPersoonById(persoon.getPersoonnr()).getVoornaam()).isEqualTo("Rense2");
    }

    @Test
    public void testGetPersonen() {
        persoonRepository.addPersoon(new Persoon("Rense2", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456787", LocalDate.of(1990, 10, 12)));

        assertThat(persoonRepository.getPersonen()).hasSize(2);
    }

    @Test
    public void testUpdatePersoonById() {
        Persoon persoon = persoonRepository.addPersoon(new Persoon("Rense3", "Houwing", "De buren", "12", "8402 GH", "Drachten", "123456709", LocalDate.of(1990, 10, 12)));

        assertThat(persoonRepository.getPersonen()).hasSize(2);
        assertThat(persoonRepository.getPersoonById(persoon.getPersoonnr()).getVoornaam()).isEqualTo("Rense3");

        persoonRepository.updatePersoonById(new Persoon(persoon.getPersoonnr(), "Henk", "Houwing", "De buren", "12", "8402 GH", "Drachten", "123456709", LocalDate.of(1990, 10, 12)));

        assertThat(persoonRepository.getPersonen()).hasSize(2);
        assertThat(persoonRepository.getPersoonById(persoon.getPersoonnr()).getVoornaam()).isEqualTo("Henk");
    }
}
