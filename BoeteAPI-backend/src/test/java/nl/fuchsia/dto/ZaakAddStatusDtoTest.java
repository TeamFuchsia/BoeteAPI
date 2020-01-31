package nl.fuchsia.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ZaakAddStatusDtoTest {

	private static Validator validator;

	@BeforeAll
	public static void setupAll() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void statusNrIsNull() {
		ZaakAddStatusDto zaakAddStatusDto = new ZaakAddStatusDto(0);

		Set<ConstraintViolation<ZaakAddStatusDto>> constraintViolations = validator.validate(zaakAddStatusDto);

		assertThat(constraintViolations).hasSize(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Statusnummer dient groter dan 0 te zijn");
	}
}
