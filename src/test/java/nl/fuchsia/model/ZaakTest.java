package nl.fuchsia.model;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ZaakTest {

    private static Validator validator;
    private static Zaak zaak;

    @BeforeClass
    public static void setupAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void zaakAdded() {
        zaak = new Zaak(1, LocalDate.of(2019, 2, 18), "A32 Richting Leeuwarden t.h.v. hectometerpaal 14.2");

        Set<ConstraintViolation<Zaak>> constraintViolations = validator.validate(zaak);

        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void pleegLocatieHasToManyCharacters() {
        zaak = new Zaak(1, LocalDate.of(2019, 2, 18), new String(new byte[101]));

        Set<ConstraintViolation<Zaak>> constraintViolations =
                validator.validate(zaak);

        assertThat(constraintViolations.size()).isEqualTo(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Meer dan 100 tekens in pleeglocatie! Pleeglocatie mag maximaal 100 tekens bevatten");
    }
}