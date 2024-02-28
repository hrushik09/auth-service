package io.hrushik09.authservice.users.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @NullAndEmptySource
    void usernameShouldBeValid(String username) {
        CreateUserRequest request = new CreateUserRequest(username, "randomPassword", List.of("api:random"));

        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message")
                .containsExactly("username should be non-empty");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void passwordShouldBeValid(String password) {
        CreateUserRequest request = new CreateUserRequest("random-username", password, List.of("api:random"));

        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message")
                .containsExactly("password should be non-empty");
    }

    @Test
    void authoritiesShouldContainAtLeastOneElement() {
        List<String> authorities = List.of();
        CreateUserRequest request = new CreateUserRequest("random-username", "randomPassword", authorities);

        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message")
                .containsExactly("should have at least one authority");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void eachAuthorityShouldBeValid(String name) {
        List<String> authorities = new ArrayList<>();
        authorities.add(name);

        CreateUserRequest request = new CreateUserRequest("random-username", "randomPassword", authorities);

        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message")
                .containsExactly("each authority name should be non-empty");
    }

    @Test
    void authoritiesListShouldContainUniqueEntries() {
        List<String> authorities = List.of("firstUnique", "firstDuplicate", "secondDuplicate", "firstDuplicate", "secondUnique", "secondDuplicate");
        CreateUserRequest request = new CreateUserRequest("random-username", "random-password", authorities);

        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message")
                .containsExactly("authorities should be unique");
    }
}
