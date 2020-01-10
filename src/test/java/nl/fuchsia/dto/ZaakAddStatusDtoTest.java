package nl.fuchsia.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import javax.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ZaakAddStatusDtoTest {

    @Test
    void statusNrIsNull() {
        ZaakAddStatusDto zaakAddStatusDto = new ZaakAddStatusDto(0);

        Set<ConstraintViolation<ZaakDto>> constraintViolations =
                validator.validate(zaakDto);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Overtredingsdatum dient te zijn gevuld!");

    }
}