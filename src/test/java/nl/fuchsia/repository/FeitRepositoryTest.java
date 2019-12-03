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
        tweedeFeit.setFeitcode("VBF-001");
        tweedeFeit.setOmschrijving("Omschrijving eerste Feit");
        tweedeFeit.setBedrag(10.00);

        feitRepository.addFeit(eersteFeit);
        feitRepository.addFeit(tweedeFeit);
        List<Feit> feitList = feitRepository.getfeiten();

        assertThat(feitList.get(0)).isNotEqualTo(tweedeFeit);

        eersteFeit.setFeitNr(2);

        assertThat(feitList.get(1)).isEqualTo(eersteFeit);
    }
}