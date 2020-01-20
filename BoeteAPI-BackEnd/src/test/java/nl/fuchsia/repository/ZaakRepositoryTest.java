package nl.fuchsia.repository;

import java.time.LocalDate;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.fuchsia.configuration.TestDatabaseConfig;
import nl.fuchsia.model.Zaak;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDatabaseConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ZaakRepositoryTest {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Autowired
    private ZaakRepository zaakRepository;

    @BeforeEach
    public void setup() {
        entityManager.createQuery("Delete FROM Zaak").executeUpdate();

        Zaak zaak = new Zaak(LocalDate.of(2019, 12, 12), "Drachten");
        zaakRepository.addZaak(zaak);
    }

    @Test
    void testAddZaak() {
        Zaak zaak = new Zaak(LocalDate.of(2019, 12, 12), "Leeuwarden");
        zaakRepository.addZaak(zaak);

        assertThat(zaakRepository.getZaken()).hasSize(2);
    }

    @Test
    void testGetZakenById() {
        Zaak zaakByIdtest = new Zaak(LocalDate.of(2019, 12, 12), "Heerenveen");
        zaakRepository.addZaak(zaakByIdtest);

        assertThat(zaakRepository.getZaken()).hasSize(2);
        assertThat(zaakRepository.getZaakById(zaakByIdtest.getZaaknr()).getPleeglocatie()).isEqualTo("Heerenveen");
    }

    @Test
    void testGetzaken() {
        assertThat(zaakRepository.getZaken()).hasSize(1);
    }
}
