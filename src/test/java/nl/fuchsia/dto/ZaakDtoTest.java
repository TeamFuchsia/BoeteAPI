package nl.fuchsia.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ZaakDtoTest {

	private static Validator validator;
	private static ZaakDto zaakDto;

	@BeforeAll
	public static void setupAll() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void pleegLocatieHasToManyCharacters() {
		zaakDto = new ZaakDto(1, LocalDate.of(2019, 2, 18), new String(new byte[101]),1, new ArrayList<Integer>(Arrays.asList(1,2)));

		Set<ConstraintViolation<ZaakDto>> constraintViolations =
			validator.validate(zaakDto);

		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Meer dan 100 tekens in pleeglocatie! Pleeglocatie mag maximaal 100 tekens bevatten");
	}

	@Test
	public void testOvertredingsdatumIsNull (){
		zaakDto = new ZaakDto(1, null, new String(new byte[10]),1, new ArrayList<Integer>(Arrays.asList(1,2)));

		Set<ConstraintViolation<ZaakDto>> constraintViolations =
			validator.validate(zaakDto);

		assertThat(constraintViolations).hasSize(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Overtredingsdatum dient te zijn gevuld!");
	}

	@Test
	public void testFeitListNotNull (){
		zaakDto = new ZaakDto(1, LocalDate.of(2019, 2, 18), new String(new byte[10]),1, null);

		Set<ConstraintViolation<ZaakDto>> constraintViolations =
			validator.validate(zaakDto);

		assertThat(constraintViolations).hasSize(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Voeg minimaal 1 feit toe.");
	}

	@Test
	public void testFeitListIsEmpty (){
		zaakDto = new ZaakDto(1, LocalDate.of(2019, 2, 18), new String(new byte[10]),1, new ArrayList<Integer>(Collections.emptyList()));

		Set<ConstraintViolation<ZaakDto>> constraintViolations =
			validator.validate(zaakDto);

		assertThat(constraintViolations).hasSize(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Voeg minimaal 1 feit toe.");
	}
}
