package nl.fuchsia.repository;

import nl.fuchsia.configuration.TestDatabaseConfig;
import nl.fuchsia.model.Zaak;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDatabaseConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ZaakRepositoryTest {

    @Autowired
    private ZaakRepository zaakRepository;

    @BeforeAll
	public void setup() {

		Zaak zaak = new Zaak(LocalDate.of(2019, 12, 12), "Drachten");
		zaakRepository.addZaak(zaak);
	}

    @Test
    void addZaak() {
		Zaak zaak = new Zaak(LocalDate.of(2019, 12, 12), "Leeuwarden");
		zaakRepository.addZaak(zaak);

        assertThat(zaakRepository.getZaken()).hasSize(2);

    }

    @Test

    void testGetZaakById() {
		Zaak zaakByIdtest = new Zaak(LocalDate.of(2019, 12, 12), "Heerenveen");
		zaakRepository.addZaak(zaakByIdtest);

    	assertThat(zaakRepository.getZaakById(zaakByIdtest.getZaaknr()).getPleeglocatie()).isEqualTo("Heerenveen");
    }

	@Test
	void getzaken() {
		assertThat(zaakRepository.getZaken()).hasSize(3);
	}
}
