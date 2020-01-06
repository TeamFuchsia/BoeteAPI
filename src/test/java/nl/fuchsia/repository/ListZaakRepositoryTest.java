package nl.fuchsia.repository;

import nl.fuchsia.model.Zaak;
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
        String pleegLocatie = "A32, ter hoogte van hectometerpaal 13.4 richting Leeuwarden";
        zaakEen.setPleeglocatie(pleegLocatie);
        zaakTwee.setOvertredingsdatum(LocalDate.of(2019, 3, 12));
        zaakTwee.setPleeglocatie(pleegLocatie);

        listZaakReposistory.addZaak(zaakEen);
        listZaakReposistory.addZaak(zaakTwee);
        List<Zaak> zaakList = listZaakReposistory.getZaken();

        assertThat(zaakList.get(0)).isEqualTo(zaakEen);

        zaakEen.setZaaknr(2);

        assertThat(zaakList.get(1)).isEqualTo(zaakEen);
    }
}
