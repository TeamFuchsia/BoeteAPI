package nl.fuchsia.repository;

import nl.fuchsia.model.Zaak;
import nl.fuchsia.repository.histRepos.ListZaakReposistory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ListZaakRepositoryTest {

    @Test
    public void testAddZaak() {
        ListZaakReposistory listZaakReposistory = new ListZaakReposistory();
        Zaak zaakEen = new Zaak();
        Zaak zaakTwee = new Zaak();
        zaakEen.setOvertredingsdatum(LocalDate.of(2019, 3, 12));
        zaakEen.setPleeglocatie("A32, ter hoogte van hectometerpaal 13.4 richting Leeuwarden");
        zaakTwee.setOvertredingsdatum(LocalDate.of(2019, 3, 12));
        zaakTwee.setPleeglocatie("A32, ter hoogte van hectometerpaal 13.4 richting Leeuwarden"); // TODO d.c.

        listZaakReposistory.addZaak(zaakEen);
        listZaakReposistory.addZaak(zaakTwee);
        List<Zaak> zaakList = listZaakReposistory.getZaken();

        assertThat(zaakList.get(0)).isEqualTo(zaakEen);

        zaakEen.setZaaknr(2);

        assertThat(zaakList.get(1)).isEqualTo(zaakEen);
    }
}
