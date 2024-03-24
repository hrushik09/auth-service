package io.hrushik09.authservice.clients.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Set;

import static io.hrushik09.authservice.clients.dto.CreateClientRequestBuilder.aRequest;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateClientRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static void hasSingleMessage(Set<ConstraintViolation<CreateClientRequest>> violations, String message) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message")
                .containsExactly(message);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void pidShouldBeNonEmpty(String id) {
        CreateClientRequest request = aRequest().withPid(id).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "pid should be non-blank");
    }

    @Test
    void pidShouldBeNonBlank() {
        CreateClientRequest request = aRequest().withPid("   ").build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "pid should be non-blank");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void clientIdShouldBeNonEmpty(String clientId) {
        CreateClientRequest request = aRequest().withClientId(clientId).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "clientId should be non-blank");
    }

    @Test
    void clientIdShouldBeNonBlank() {
        CreateClientRequest request = aRequest().withClientId("  ").build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "clientId should be non-blank");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void clientSecretShouldBeNonEmpty(String clientSecret) {
        CreateClientRequest request = aRequest().withClientSecret(clientSecret).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "clientSecret should be non-blank");
    }

    @Test
    void clientSecretShouldBeNonBlank() {
        CreateClientRequest request = aRequest().withClientSecret("   ").build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "clientSecret should be non-blank");
    }

    @Test
    void clientAuthenticationMethodShouldBeNonNull() {
        CreateClientRequest request = aRequest().withClientAuthenticationMethod(null).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "clientAuthenticationMethod should be non-null");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void scopeShouldBeNonEmpty(String scope) {
        CreateClientRequest request = aRequest().withScope(scope).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "scope should be non-blank");
    }

    @Test
    void scopeShouldBeNonBlank() {
        CreateClientRequest request = aRequest().withScope("   ").build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "scope should be non-blank");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void redirectUriShouldBeNonEmpty(String redirectUri) {
        CreateClientRequest request = aRequest().withRedirectUri(redirectUri).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "redirectUri should be non-blank");
    }

    @Test
    void redirectUriShouldBeNonBlank() {
        CreateClientRequest request = aRequest().withRedirectUri("   ").build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "redirectUri should be non-blank");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void authorizationGrantTypeShouldBeNonEmpty(String authorizationGrantType) {
        CreateClientRequest request = aRequest().withAuthorizationGrantType(authorizationGrantType).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "authorizationGrantType should be non-blank");
    }

    @Test
    void authorizationGrantTypeShouldBeNonBlank() {
        CreateClientRequest request = aRequest().withAuthorizationGrantType("   ").build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "authorizationGrantType should be non-blank");
    }
}
