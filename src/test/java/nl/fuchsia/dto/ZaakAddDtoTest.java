package nl.fuchsia.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ZaakAddDtoTest {

	private static Validator validator;
	private static ZaakAddDto zaakAddDto;

	@BeforeAll
	public static void setupAll() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void pleegLocatieHasToManyCharacters() {
		zaakAddDto = new ZaakAddDto(1, LocalDate.of(2019, 2, 18), new String(new byte[101]), 1, new ArrayList<>(Arrays.asList(1, 2)));

		Set<ConstraintViolation<ZaakAddDto>> constraintViolations =
			validator.validate(zaakAddDto);

		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Meer dan 100 tekens in pleeglocatie! Pleeglocatie mag maximaal 100 tekens bevatten");
	}

	@Test
	public void testOvertredingsdatumIsNull() {
		zaakAddDto = new ZaakAddDto(1, null, new String(new byte[10]), 1, new ArrayList<>(Arrays.asList(1, 2)));

		Set<ConstraintViolation<ZaakAddDto>> constraintViolations =
			validator.validate(zaakAddDto);

		assertThat(constraintViolations).hasSize(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Overtredingsdatum dient te zijn gevuld!");
	}

	@Test
	public void testFeitListNotNull() {
		zaakAddDto = new ZaakAddDto(1, LocalDate.of(2019, 2, 18), new String(new byte[10]), 1, null);

		Set<ConstraintViolation<ZaakAddDto>> constraintViolations =
			validator.validate(zaakAddDto);

		assertThat(constraintViolations).hasSize(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Voeg minimaal 1 feit toe.");
	}

	@Test
	public void testFeitListIsEmpty() {
		zaakAddDto = new ZaakAddDto(1, LocalDate.of(2019, 2, 18), new String(new byte[10]), 1, new ArrayList<>(Collections.emptyList()));

		Set<ConstraintViolation<ZaakAddDto>> constraintViolations =
			validator.validate(zaakAddDto);

		assertThat(constraintViolations).hasSize(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Voeg minimaal 1 feit toe.");
	}
}
