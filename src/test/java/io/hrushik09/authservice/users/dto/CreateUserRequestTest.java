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

import static io.hrushik09.authservice.users.dto.CreateUserRequestBuilder.aRequest;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static void hasSingleMessage(Set<ConstraintViolation<CreateUserRequest>> violations, String x) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message")
                .containsExactly(x);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void usernameShouldBeNonEmpty(String username) {
        CreateUserRequest request = aRequest().withUsername(username).build();
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "username should be non-blank");
    }

    @Test
    void usernameShouldBeNonBlank() {
        CreateUserRequest request = aRequest().withUsername("   ").build();
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "username should be non-blank");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void passwordShouldBeNonEmpty(String password) {
        CreateUserRequest request = aRequest().withPassword(password).build();
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "password should be non-blank");
    }

    @Test
    void passwordShouldBeNonBlank() {
        CreateUserRequest request = aRequest().withPassword("   ").build();
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "password should be non-blank");
    }

    @Test
    void authoritiesShouldContainAtLeastOneElement() {
        List<String> authorities = List.of();
        CreateUserRequest request = aRequest().withAuthorities(authorities).build();
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "should contain at least one authority");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void eachAuthorityShouldBeNonEmpty(String name) {
        List<String> authorities = new ArrayList<>();
        authorities.add(name);
        CreateUserRequest request = aRequest().withAuthorities(authorities).build();
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "each authority name should be non-blank");
    }

    @Test
    void eachAuthorityShouldBeNonBlank() {
        List<String> authorities = new ArrayList<>();
        authorities.add("   ");
        CreateUserRequest request = aRequest().withAuthorities(authorities).build();
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
