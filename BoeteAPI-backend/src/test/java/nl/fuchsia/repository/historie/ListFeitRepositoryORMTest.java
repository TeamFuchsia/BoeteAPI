package nl.fuchsia.repository.historie;

import nl.fuchsia.model.Feit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ListFeitRepositoryORMTest {
    @Test
    public void testAddFeit() {
        ListFeitRepository listFeitRepository = new ListFeitRepository();
        Feit eersteFeit = new Feit();
        Feit tweedeFeit = new Feit();
        eersteFeit.setFeitcode("VBF-001");
        eersteFeit.setOmschrijving("Omschrijving eerste Feit");
        eersteFeit.setBedrag(10.00);
        tweedeFeit.setFeitcode("VBF-001");
        tweedeFeit.setOmschrijving("Omschrijving eerste Feit");
        tweedeFeit.setBedrag(10.00);

        listFeitRepository.addFeit(eersteFeit);
        listFeitRepository.addFeit(tweedeFeit);
        List<Feit> feitList = listFeitRepository.getfeiten();

        assertThat(feitList.get(0)).isNotEqualTo(tweedeFeit);

        eersteFeit.setFeitnr(2);

        assertThat(feitList.get(1)).isEqualTo(eersteFeit);
    }
}
