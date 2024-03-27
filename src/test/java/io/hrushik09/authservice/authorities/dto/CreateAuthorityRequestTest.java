package io.hrushik09.authservice.authorities.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateAuthorityRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @MethodSource("io.hrushik09.authservice.setup.ParameterizedTestParams#blankStrings")
    void nameShouldBeNonBlank(String name) {
        CreateAuthorityRequest request = new CreateAuthorityRequest(name);

        Set<ConstraintViolation<CreateAuthorityRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message")
                .containsExactly("name should be non-blank");
    }
}
