package io.hrushik09.authservice.authorities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateAuthorityRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @NullAndEmptySource
    void nameShouldBeValid(String name) {
        CreateAuthorityRequest request = new CreateAuthorityRequest(name);

        Set<ConstraintViolation<CreateAuthorityRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message")
                .containsExactly("name should be non-empty");
    }
}
