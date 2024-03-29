package io.hrushik09.authservice.users.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static io.hrushik09.authservice.users.dto.CreateUserRequestBuilder.aRequest;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static void hasSingleMessage(Set<ConstraintViolation<CreateUserRequest>> violations, String message) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message")
                .containsExactly(message);
    }

    private static void hasCountViolationsWithOneOfThemAs(Set<ConstraintViolation<CreateUserRequest>> violations, int count, String message) {
        assertThat(violations).hasSize(count);
        assertThat(violations).extracting("message")
                .contains(message);
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.authservice.setup.ParameterizedTestParams#blankStrings")
    void usernameShouldBeNonBlank(String username) {
        CreateUserRequest request = aRequest().withUsername(username).build();
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "username should be non-blank");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.authservice.setup.ParameterizedTestParams#blankStrings")
    void passwordShouldBeNonBlank(String password) {
        CreateUserRequest request = aRequest().withPassword(password).build();
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "password should be non-blank");
    }

    @Test
    void authoritiesShouldBeNonNull() {
        CreateUserRequest request = aRequest().withAuthorities(null).build();
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
        hasCountViolationsWithOneOfThemAs(violations, 2, "authorities should be non-null");
    }

    @Test
    void authoritiesShouldContainAtLeastOneElement() {
        CreateUserRequest request = aRequest().withAuthorities(List.of()).build();
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "authorities should contain at least one element");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.authservice.setup.ParameterizedTestParams#blankStrings")
    void eachAuthorityShouldBeNonBlank(String name) {
        CreateUserRequest request = aRequest().withAuthorities(Collections.singletonList(name)).build();
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "each authority name should be non-blank");
    }

    @Test
    void authoritiesListShouldContainUniqueEntries() {
        List<String> authorities = List.of("firstUnique", "firstDuplicate", "secondDuplicate", "firstDuplicate", "secondUnique", "secondDuplicate");
        CreateUserRequest request = aRequest().withAuthorities(authorities).build();
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "authorities should be unique");
    }
}
