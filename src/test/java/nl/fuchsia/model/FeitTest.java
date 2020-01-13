package nl.fuchsia.model;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FeitTest {

    private static Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testFeitIsValid() {
        Feit feit = new Feit(1, "VBF-000", "Test", 4.00);
        Set<ConstraintViolation<Feit>> constraintViolations =
                validator.validate(feit);

        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void testFeitcodeIsNull() {
        Feit feit = new Feit(1, null, "Test", 4.00);
        Set<ConstraintViolation<Feit>> constraintViolations =
                validator.validate(feit);

        assertThat(constraintViolations.size()).isEqualTo(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Feitcode mist, voeg deze nog toe");
    }

    @Test
    public void testFeitcodeWrongPattern() {
        //test voor getal missend in feit inclusief goede tekst afdrukken
        Feit feit = new Feit(1, "VBF-00", "Test", 4.00);
        Set<ConstraintViolation<Feit>> constraintViolations =
                validator.validate(feit);

        assertThat(constraintViolations.size()).isEqualTo(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Feitcode moet voldoen aan de standaard opmaak, VBF- gevolgd door 3 cijfers, bv VBF-000");

        //test voor geen streepje in feitcode
        feit.setFeitcode("VBF000");

        assertThat(constraintViolations.size()).isEqualTo(1);

        //test voor streepje op verkeerde plek in feitcode
        feit.setFeitcode("VB-F000");

        assertThat(constraintViolations.size()).isEqualTo(1);

        //test geen hoofdletters in feitcode
        feit.setFeitcode("vbf-000");

        assertThat(constraintViolations.size()).isEqualTo(1);

        //test voor verkeeerde volgorde letters
        feit.setFeitcode("FBV-000");

        assertThat(constraintViolations.size()).isEqualTo(1);

        //test voor  missende letter in feitcode
        feit.setFeitcode("VB-000");

        assertThat(constraintViolations.size()).isEqualTo(1);

        //test voor  verkeerde letter in feitcode
        feit.setFeitcode("CBF-000");

        assertThat(constraintViolations.size()).isEqualTo(1);

        //test voor letter ipv getal in feitcode
        feit.setFeitcode("VBf-0Q0");

        assertThat(constraintViolations.size()).isEqualTo(1);
    }

    @Test
    public void testOmschrijvingIsNull() {
        Feit feit = new Feit(1, "VBF-000", null, 4.00);
        Set<ConstraintViolation<Feit>> constraintViolations =
                validator.validate(feit);

        assertThat(constraintViolations.size()).isEqualTo(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Omschrijving mist, voeg deze nog toe");
    }

    @Test
    public void testOmschrijvingIsToLong() {
        Feit feit =  new Feit(1, "VBF-000", new String(new char[5001]).replace('\0','F'), 4.00);
        Set<ConstraintViolation<Feit>> constraintViolations =
                validator.validate(feit);

        assertThat(constraintViolations.size()).isEqualTo(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Omschrijving mag niet meer dan 5000 tekens bevatten");
    }

    @Test
    public void testBedragIsNull() {
        Feit feit = new Feit(1, "VBF-000", "test", 0.00);
        Set<ConstraintViolation<Feit>> constraintViolations =
                validator.validate(feit);

        assertThat(constraintViolations.size()).isEqualTo(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Bedrag mag niet 0.00 of negatief zijn");
    }

    @Test
    public void testBedragIsNegative() {
        Feit feit = new Feit(1, "VBF-000", "test", -1.23);
        Set<ConstraintViolation<Feit>> constraintViolations =
                validator.validate(feit);

        assertThat(constraintViolations.size()).isEqualTo(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Bedrag mag niet 0.00 of negatief zijn");
    }

    @Test
    public void testBedragIsToLong() {
        Feit feit = new Feit(1, "VBF-000", "test", 100000000000.00);
        Set<ConstraintViolation<Feit>> constraintViolations =
                validator.validate(feit);

        assertThat(constraintViolations.size()).isEqualTo(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Bedrag moet 2 decimalen bevatten en kleiner dan 100.000.000.000");
    }

    @Test
    public void testBedragHasToManyDecimals() {
        Feit feit = new Feit(1, "VBF-000", "test", 5.111);
        Set<ConstraintViolation<Feit>> constraintViolations =
                validator.validate(feit);

        assertThat(constraintViolations.size()).isEqualTo(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Bedrag moet 2 decimalen bevatten en kleiner dan 100.000.000.000");
    }
}