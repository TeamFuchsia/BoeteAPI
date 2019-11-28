package nl.fuchsia.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FeitTest {
    @Test
    public void testToString() {
        Feit feit = new Feit(1, "VBF-000", "Omschrijving van strafbaar feit.", 100.00);

        assertThat(feit.toString()).isEqualTo("Feit{feitNr=1, feitcode=VBF-000, omschrijving=Omschrijving van strafbaar feit., bedrag=100.0}");
    }
}