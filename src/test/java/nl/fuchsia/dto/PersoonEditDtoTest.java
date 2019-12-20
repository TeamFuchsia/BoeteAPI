package nl.fuchsia.dto;

import java.time.LocalDate;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersoonEditDtoTest {

    private static Validator validator;
    private static PersoonEditDto persoonEditDto;

    @BeforeAll
    public static void setupAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void setup() {
        persoonEditDto = new PersoonEditDto(2, "Rense", "Houwing", "Voltawerk2", "36", "8401 EN", "Gorredijk", "123456789", LocalDate.of(1967, 10, 12));
    }

    @Test
    public void testVoornaamIsNullOrBlank() {
        persoonEditDto.setVoornaam(null);

        Set<ConstraintViolation<PersoonEditDto>> constraintViolations = validator.validate(persoonEditDto);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Voornaam moet ingevuld zijn");

        persoonEditDto.setVoornaam("  ");
        constraintViolations = validator.validate(persoonEditDto);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Voornaam moet ingevuld zijn");
    }

    @Test
    public void testPersoonnrIsNull() {
        persoonEditDto = new PersoonEditDto("Rense", "Houwing", "Voltawerk2", "36", "8401 EN", "Gorredijk", "123456789", LocalDate.of(1967, 10, 12));

        Set<ConstraintViolation<PersoonEditDto>> constraintViolations = validator.validate(persoonEditDto);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Persoonr moet ingevuld zijn");
    }

    @Test
    public void testPostcodeError() {
        persoonEditDto.setPostcode("8401EN");

        Set<ConstraintViolation<PersoonEditDto>> constraintViolations = validator.validate(persoonEditDto);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Voer een geldige postcode in. 4 cijfers, een spatie en 2 hoofdletters");

        persoonEditDto.setPostcode("8404 as");

        constraintViolations = validator.validate(persoonEditDto);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Voer een geldige postcode in. 4 cijfers, een spatie en 2 hoofdletters");
    }

    @Test
    void testBsnError() {
        persoonEditDto.setBsn("12345678");

        Set<ConstraintViolation<PersoonEditDto>> constraintViolations = validator.validate(persoonEditDto);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Voer een geldig 9 cijferig BSN nummer in.");
    }
}