package nl.fuchsia.repository;

import nl.fuchsia.model.Feit;
import org.junit.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FeitRepositoryTest {
    @Test
    public void testAddFeit() {
        FeitRepository feitRepository = new FeitRepository();
        Feit eersteFeit = new Feit();
        Feit tweedeFeit = new Feit();
        eersteFeit.setFeitcode("VBF-001");
        eersteFeit.setOmschrijving("Omschrijving eerste Feit");
        eersteFeit.setBedrag(10.00);
        tweedeFeit.setFeitcode("VBF-002");
        tweedeFeit.setOmschrijving("Omschrijving tweede Feit");
        tweedeFeit.setBedrag(20.00);

        feitRepository.addFeit(eersteFeit);
        feitRepository.addFeit(tweedeFeit);
        List<Feit> feitList = feitRepository.getfeiten();

        assertThat(feitList.get(0).toString()).isEqualTo("Feit{feitNr=1, feitcode=VBF-001, omschrijving=Omschrijving eerste Feit, bedrag=10.0}");
        assertThat(feitList.get(1).toString()).isEqualTo("Feit{feitNr=2, feitcode=VBF-002, omschrijving=Omschrijving tweede Feit, bedrag=20.0}");
    }
}