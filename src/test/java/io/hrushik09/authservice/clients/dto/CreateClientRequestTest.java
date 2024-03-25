package io.hrushik09.authservice.clients.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.hrushik09.authservice.clients.dto.CreateClientRequestBuilder.aRequest;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateClientRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static void hasOneViolationWithMessage(Set<ConstraintViolation<CreateClientRequest>> violations, String message) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message")
                .containsExactly(message);
    }

    private static void hasTwoViolationsWithOneOfThemAs(Set<ConstraintViolation<CreateClientRequest>> violations, String message) {
        assertThat(violations).hasSize(2);
        assertThat(violations).extracting("message")
                .contains(message);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void pidShouldBeNonEmpty(String id) {
        CreateClientRequest request = aRequest().withPid(id).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "pid should be non-blank");
    }

    @Test
    void pidShouldBeNonBlank() {
        CreateClientRequest request = aRequest().withPid("   ").build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "pid should be non-blank");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void clientIdShouldBeNonEmpty(String clientId) {
        CreateClientRequest request = aRequest().withClientId(clientId).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "clientId should be non-blank");
    }

    @Test
    void clientIdShouldBeNonBlank() {
        CreateClientRequest request = aRequest().withClientId("  ").build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "clientId should be non-blank");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void clientSecretShouldBeNonEmpty(String clientSecret) {
        CreateClientRequest request = aRequest().withClientSecret(clientSecret).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "clientSecret should be non-blank");
    }

    @Test
    void clientSecretShouldBeNonBlank() {
        CreateClientRequest request = aRequest().withClientSecret("   ").build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "clientSecret should be non-blank");
    }

    @Test
    void clientAuthenticationMethodShouldBeNonNull() {
        CreateClientRequest request = aRequest().withClientAuthenticationMethod(null).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "clientAuthenticationMethod should be non-null");
    }

    @Test
    void scopesShouldContainAtLeastOneElement() {
        List<String> scopes = List.of();
        CreateClientRequest request = aRequest().withScopes(scopes).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasTwoViolationsWithOneOfThemAs(violations, "should contain at least one scope");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void eachScopeShouldBeNonEmpty(String scope) {
        List<String> scopes = new ArrayList<>();
        scopes.add(scope);
        CreateClientRequest request = aRequest().withScopes(scopes).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasTwoViolationsWithOneOfThemAs(violations, "each scope should be non-blank");
    }

    @Test
    void eachScopeShouldBeNonBlank() {
        List<String> scopes = new ArrayList<>();
        scopes.add("    ");
        CreateClientRequest request = aRequest().withScopes(scopes).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasTwoViolationsWithOneOfThemAs(violations, "each scope should be non-blank");
    }

    @Test
    void scopesListShouldContainUniqueEntries() {
        List<String> scopes = List.of("firstDuplicateScope", "firstUniqueScope", "firstDuplicateScope", "secondDuplicateScope", "secondDuplicateScope");
        CreateClientRequest request = aRequest().withScopes(scopes).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasTwoViolationsWithOneOfThemAs(violations, "scopes should be unique");
    }

    @Test
    void scopesShouldContainOpenidScopeAlways() {
        List<String> scopes = List.of("randomScope");
        CreateClientRequest request = aRequest().withScopes(scopes).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "scopes should contain OPENID scope");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void redirectUriShouldBeNonEmpty(String redirectUri) {
        CreateClientRequest request = aRequest().withRedirectUri(redirectUri).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "redirectUri should be non-blank");
    }

    @Test
    void redirectUriShouldBeNonBlank() {
        CreateClientRequest request = aRequest().withRedirectUri("   ").build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "redirectUri should be non-blank");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void authorizationGrantTypeShouldBeNonEmpty(String authorizationGrantType) {
        CreateClientRequest request = aRequest().withAuthorizationGrantType(authorizationGrantType).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "authorizationGrantType should be non-blank");
    }

    @Test
    void authorizationGrantTypeShouldBeNonBlank() {
        CreateClientRequest request = aRequest().withAuthorizationGrantType("   ").build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "authorizationGrantType should be non-blank");
    }
}
