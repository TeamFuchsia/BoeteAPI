package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PersoonRepositoryTest {

    private PersoonRepository persoonRepository = new PersoonRepository();
    private List<Persoon> personen = new ArrayList<>();

    @BeforeAll
    public static void setUpAll() throws Exception {

    }

    @BeforeEach
    public void setUp() {
        personen.add(new Persoon(1, "Rense", "Houwing", "Voltawerk", "36"
                , "8401 EN", "Gorredijk", "123456789", LocalDate.of(1967, 10, 12)));
        personen.add(new Persoon(1, "Rense", "Houwing", "Voltawerk", "36"
                , "8401 EN", "Gorredijk", "123456789", LocalDate.of(1967, 10, 12)));
    }

    /**
     * Test het aantal records in de personen repository. (hoort niet in de user story 1-RH.)
     */
    @Test
    public void testGetPersonen() {
        assertThat(personen).hasSize(2);
    }

    /**
     * Test of een persoon wordt toegevoegd.
     */
    @Test
    public void testAddPersoon() {
        assertThat(persoonRepository.getPersonen()).hasSize(0);
        Persoon persoon = new Persoon(6, "Rense", "Houwing", "Voltawerk", "36"
                , "8401 EN", "Gorredijk", "123456789", LocalDate.of(1967, 10, 12));
        persoonRepository.addPersoon(persoon);
        assertThat(persoonRepository.getPersonen()).hasSize(1);
    }

    /**
     * Test of 2 personen in de personen repository identiek zijn.
     */
    @Test
    public void testGelijkePersoon() {
        assertThat(personen.get(0)).isEqualTo(personen.get(1));
        personen.get(0).setVoornaam("Klaas");
        assertThat(personen.get(0)).isNotEqualTo(personen.get(1));
        assertThat(personen).hasSize(2);
    }
}