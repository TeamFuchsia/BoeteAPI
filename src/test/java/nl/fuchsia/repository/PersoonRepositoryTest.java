package nl.fuchsia.repository;

import nl.fuchsia.configuration.TestDatabaseConfig;
import nl.fuchsia.model.Persoon;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDatabaseConfig.class)
@Transactional
public class PersoonRepositoryTest {

	@Autowired
	private PersoonRepository persoonRepository;

    @Test // TODO test zou onafhankelijk moeten en in een transactie moeten lopen <--
    public void testAddPersoon() {
        persoonRepository.addPersoon(new Persoon(
                "Rense1", "Houwing", "De buren", "12", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12)));

        persoonRepository.addPersoon(new Persoon(
                "Rense2", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456787", LocalDate.of(1990, 10, 12)));


		assertThat(persoonRepository.getPersonen()).hasSize(2);

		persoonRepository.addPersoon(new Persoon(1, "Rense3", "Houwing", "De buren", "10", "8402 GH", "Drachten", "124456788", LocalDate.of(1990, 10, 12)));

		assertThat(persoonRepository.getPersonen()).hasSize(3);
		assertThat(persoonRepository.getPersoonById(1).getVoornaam()).isNotEqualTo("Rense1");
	}

    @Test()
    public void testGetPersoonById() {
        persoonRepository.addPersoon(new Persoon(
                "Rense1", "Houwing", "De buren", "12", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12)));

        assertThat(persoonRepository.getPersonen()).hasSize(1);
        assertThat(persoonRepository.getPersoonById(7).getVoornaam()).isEqualTo("Rense1");
    }

    @Test
    public void testGetPersonen() {
        persoonRepository.addPersoon(new Persoon(
                "Rense1", "Houwing", "De buren", "12", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12)));

        persoonRepository.addPersoon(new Persoon(
                "Rense2", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456787", LocalDate.of(1990, 10, 12)));

        assertThat(persoonRepository.getPersonen()).hasSize(2);
    }

    @Test
    public void testUpdatePersoonById() {
        persoonRepository.addPersoon(new Persoon(
                1, "Rense1", "Houwing", "De buren", "12", "8402 GH", "Drachten", "123456709", LocalDate.of(1990, 10, 12)));

        assertThat(persoonRepository.getPersoonById(1).getVoornaam()).isEqualTo("Rense1");

        persoonRepository.updatePersoonById(new Persoon(
                1, "Henk", "Houwing", "De buren", "12", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12)));

        assertThat(persoonRepository.getPersonen()).hasSize(1);
        assertThat(persoonRepository.getPersoonById(1).getVoornaam()).isEqualTo("Henk");
    }
}