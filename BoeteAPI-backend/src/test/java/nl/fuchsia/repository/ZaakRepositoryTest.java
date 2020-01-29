package nl.fuchsia.repository;

import nl.fuchsia.Application;
import nl.fuchsia.model.Zaak;
import nl.fuchsia.repository.historie.ZaakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@SpringBootTest(classes = Application.class)
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
