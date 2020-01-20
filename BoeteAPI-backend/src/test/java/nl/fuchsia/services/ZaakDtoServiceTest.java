package nl.fuchsia.services;

import nl.fuchsia.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ZaakDtoServiceTest {

    ZaakDtoService zaakDtoService = new ZaakDtoService();

    @Test
    void setZaakDto() {
        //Maak een persoon aan om toe te voegen aan zaak
        Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));

        //Maak een feit aan, zet deze in een lijst  om deze toe te voegen aan zaak
        Feit feit = new Feit(1, "VBF-003", "Test", 4.00);
        List<Feit> feiten = new ArrayList<>();
        feiten.add(feit);

        //Maakt een zaak aan om toe te voegen aan zaakStatus en nadat zaakStatus aan zaak is gekoppeld te gebruiken in verify
        Zaak zaak = new Zaak(1, LocalDate.now(), "Leeuwarden", persoon, feiten);

        //Maakt een zaakStatus om deze weer toe te voegen aan de zaak
        ZaakStatus zaakStatus = new ZaakStatus(1, LocalDate.now(), new Status(1, "Open"), zaak);
        List<ZaakStatus> zaakStatussen = new ArrayList<>();
        zaakStatussen.add(zaakStatus);
        zaak.setZaakStatus(zaakStatussen);

        assertThat(zaakDtoService.setZaakDto(zaak).getPersoonnr()).isEqualTo(persoon.getPersoonnr());
    }
}
