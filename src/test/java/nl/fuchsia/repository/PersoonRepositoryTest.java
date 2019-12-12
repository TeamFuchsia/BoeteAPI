package nl.fuchsia.repository;

import nl.fuchsia.configuration.TestDatabaseConfig;
import nl.fuchsia.model.Persoon;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDatabaseConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersoonRepositoryTest {

    @Autowired
    private PersoonRepository persoonRepository;

    @BeforeEach
    public void Setup() {
    }

    @Test
    @Order(1)
    public void testAddPersoon() {
        persoonRepository.addPersoon(new Persoon("Rense1", "Houwing", "De buren", "12", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12)));
        persoonRepository.addPersoon(new Persoon("Rense2", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12)));

        assertThat(persoonRepository.getPersonen()).hasSize(2);

        persoonRepository.addPersoon(new Persoon(1, "Rense3", "Houwing", "De buren", "10", "8402 GH", "Drachten", "124456788", LocalDate.of(1990, 10, 12)));

        assertThat(persoonRepository.getPersonen()).hasSize(3);
        assertThat(persoonRepository.getPersoonById(1).getVoornaam()).isNotEqualTo("Rense3");
    }

    @Test
    @Order(2)
    public void testGetPersoonById() {
        assertThat(persoonRepository.getPersoonById(1).getHuisnummer()).isEqualTo("12");
        assertThat(persoonRepository.getPersonen()).hasSize(3);
    }

    @Test
    @Order(3)
    public void testGetPersonen() {
        assertThat(persoonRepository.getPersonen()).hasSize(3);
    }
}