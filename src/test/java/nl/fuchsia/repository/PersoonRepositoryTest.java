package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;

public class PersoonRepositoryTest {

    private PersoonRepository persoonRepository = new PersoonRepository();
    private List<Persoon> testPersonen = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        testPersonen.add(new Persoon(1, "Rense1", "Houwing2", "Voltawerk", "36"
                , "8401 EN", "Gorredijk", "123456789", LocalDate.of(1967, 10, 12)));
        }

    @Test
    public void testGetAllePersonen() {

        assertThat(testPersonen.get(0).getVoornaam()).isEqualTo("Rense1");
    }

    @Test
    public void testAddPersoonById() {



//        Persoon persoon2 = new Persoon(2, "Rense2", "Houwing3", "Voltawerk", "36"
//                , "8401 EN", "Gorredijk", "123456789", LocalDate.of(1967, 10, 12));
//        persoonRepository.addPersoonById(persoon2);
//        assertThat(testPersonen.get(1).getVoornaam()).isEqualTo("Rense2");
    }
}