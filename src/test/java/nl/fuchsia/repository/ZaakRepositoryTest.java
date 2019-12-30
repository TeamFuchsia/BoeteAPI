package nl.fuchsia.repository;

import nl.fuchsia.configuration.TestDatabaseConfig;
import nl.fuchsia.model.Feit;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.model.Zaak;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDatabaseConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ZaakRepositoryTest {

    @Autowired
    private ZaakRepository zaakRepository;

    @Autowired
	private PersoonRepository persoonRepository;

    @Autowired
	private FeitRepository feitRepository;

    @BeforeAll
	public void setup() {
		Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));
		persoonRepository.addPersoon(persoon);

		Feit eersteFeit = new Feit("VBF-001", "Test", 500);
		Feit tweedeFeit =  new Feit("VBF-002", "Test", 500);
		feitRepository.addFeit(eersteFeit);
		feitRepository.addFeit(tweedeFeit);
		List<Feit> feitList = new ArrayList<>();
		feitList.add(eersteFeit);
		feitList.add(tweedeFeit);

		Zaak zaak = new Zaak(LocalDate.of(2019, 12, 12), "Drachten",persoon,feitList);

		zaakRepository.addZaak(zaak);
	}

    @Test
    void addZaak() {
		Zaak zaak = new Zaak(LocalDate.of(2019, 12, 12), "Leeuwarden",persoonRepository.getPersoonById(1),feitRepository.getFeiten());

		zaakRepository.addZaak(zaak);

        assertThat(zaakRepository.getZaken()).hasSize(3);

    }

    @Test

    void testGetZaakById() {
		Zaak zaakByIdtest = new Zaak(LocalDate.of(2019, 12, 12), "Heerenveen",persoonRepository.getPersoonById(1),feitRepository.getFeiten());

		zaakRepository.addZaak(zaakByIdtest);

    	assertThat(zaakRepository.getZaakById(zaakByIdtest.getZaaknr()).getPleeglocatie()).isEqualTo("Heerenveen");
    }

	@Test
	void getzaken() {
		assertThat(zaakRepository.getZaken()).hasSize(4);
	}

	@Test
	void getZakenByPersoon() {
		Persoon persoon = persoonRepository.getPersoonById(1);

		zaakRepository.getZakenByPersoon(persoon);

		assertThat(zaakRepository.getZakenByPersoon(persoon)).hasSize(3);

		List<Feit> feitList = new ArrayList<>();
		feitList.add(feitRepository.getFeitById(2));
		zaakRepository.addZaak(new Zaak(LocalDate.of(2020, 1, 1), "Groningen",persoon,feitList));

		zaakRepository.getZakenByPersoon(persoon);

		assertThat(zaakRepository.getZakenByPersoon(persoon)).hasSize(4);
	}
}