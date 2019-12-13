package nl.fuchsia.repository;

import nl.fuchsia.configuration.TestDatabaseConfig;
import nl.fuchsia.model.Zaak;
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
public class ZaakRepositoryTest {

    @Autowired
    private ZaakRepository zaakRepository;

    @Test
    @Order(1)
    void addZaak() {
        zaakRepository.addZaak(new Zaak(LocalDate.of(2019, 12, 12), "Drachten"));

        assertThat(zaakRepository.getZaken()).hasSize(1);

        zaakRepository.addZaak(new Zaak(1, LocalDate.of(2019, 12, 12), "Sneek"));

        assertThat(zaakRepository.getZaakById(1).getPleegLocatie()).isEqualTo("Drachten");
    }

    @Test
    @Order(2)
    void getZakenById() {
        assertThat(zaakRepository.getZaakById(1).getPleegLocatie()).isEqualTo("Drachten");
    }

    @Test
    @Order(3)
    void getzaken() {
        assertThat(zaakRepository.getZaken()).hasSize(2);
    }
}