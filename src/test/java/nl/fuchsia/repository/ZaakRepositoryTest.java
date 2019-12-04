package nl.fuchsia.repository;

import nl.fuchsia.model.Zaak;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ZaakRepositoryTest {

    @Test
    public void testAddZaak() {
        ZaakReposistory zaakReposistory = new ZaakReposistory();
        Zaak zaakEen = new Zaak();
        Zaak zaakTwee = new Zaak();
        zaakEen.setOvertredingsDatum(LocalDate.of(2019, 3, 12));
        zaakEen.setPleegLocatie("A32, ter hoogte van hectometerpaal 13.4 richting Leeuwarden");
        zaakTwee.setOvertredingsDatum(LocalDate.of(2019, 4, 12));
        zaakTwee.setPleegLocatie("A7, ter hoogte van hectometerpaal 13.4 richting Groningen");

        zaakReposistory.addZaak(zaakEen);
        zaakReposistory.addZaak(zaakTwee);
        List<Zaak> zaakList = zaakReposistory.getZaken();

        assertThat(zaakList.get(0)).isEqualTo(zaakEen);
        assertThat(zaakList.get(0)).isNotEqualTo(zaakTwee);
    }
}
