package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.AuthorizationGrantType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
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

    private static void hasCountViolationsWithOneOfThemAs(Set<ConstraintViolation<CreateClientRequest>> violations, int count, String message) {
        assertThat(violations).hasSize(count);
        assertThat(violations).extracting("message")
                .contains(message);
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.authservice.setup.ParameterizedTestParams#blankStrings")
    void pidShouldBeNonBlank(String id) {
        CreateClientRequest request = aRequest().withPid(id).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "pid should be non-blank");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.authservice.setup.ParameterizedTestParams#blankStrings")
    void clientIdShouldBeNonBlank(String clientId) {
        CreateClientRequest request = aRequest().withClientId(clientId).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "clientId should be non-blank");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.authservice.setup.ParameterizedTestParams#blankStrings")
    void clientSecretShouldBeNonBlank(String clientSecret) {
        CreateClientRequest request = aRequest().withClientSecret(clientSecret).build();
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
    void scopesFieldShouldBeNonNull() {
        CreateClientRequest request = aRequest().withScopes(null).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasCountViolationsWithOneOfThemAs(violations, 3, "scopes should be non-null");
    }

    @Test
    void scopesShouldContainAtLeastOneElement() {
        CreateClientRequest request = aRequest().withScopes(List.of()).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasCountViolationsWithOneOfThemAs(violations, 2, "should contain at least one scope");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.authservice.setup.ParameterizedTestParams#blankStrings")
    void eachScopeShouldBeNonBlank(String scope) {
        CreateClientRequest request = aRequest().withScopes(Collections.singletonList(scope)).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasCountViolationsWithOneOfThemAs(violations, 2, "each scope should be non-blank");
    }

    @Test
    void scopesListShouldContainUniqueEntries() {
        List<String> scopes = List.of("firstDuplicateScope", "firstUniqueScope", "firstDuplicateScope", "secondDuplicateScope", "secondDuplicateScope");
        CreateClientRequest request = aRequest().withScopes(scopes).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasCountViolationsWithOneOfThemAs(violations, 2, "scopes should be unique");
    }

    @Test
    void scopesShouldContainOpenidScopeAlways() {
        List<String> scopes = List.of("randomScope");
        CreateClientRequest request = aRequest().withScopes(scopes).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "scopes should contain OPENID scope");
    }

    @Test
    void redirectUrisFieldShouldBeNonNull() {
        CreateClientRequest request = aRequest().withRedirectUris(null).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasCountViolationsWithOneOfThemAs(violations, 2, "redirectUris should be non-null");
    }

    @Test
    void redirectUrisShouldContainAtLeastOneElement() {
        CreateClientRequest request = aRequest().withRedirectUris(List.of()).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "should contain at least one redirectUri");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.authservice.setup.ParameterizedTestParams#blankStrings")
    void eachRedirectUriShouldBeNonBlank(String redirectUri) {
        CreateClientRequest request = aRequest().withRedirectUris(Collections.singletonList(redirectUri)).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "redirectUri should be non-blank");
    }

    @Test
    void redirectUrisListShouldContainUniqueEntries() {
        List<String> redirectUris = List.of("firstUnique", "firstDuplicate", "secondUnique", "firstDuplicate");
        CreateClientRequest request = aRequest().withRedirectUris(redirectUris).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "redirectUris should be unique");
    }

    @Test
    void authorizationGrantTypesShouldBeNonNull() {
        CreateClientRequest request = aRequest().withAuthorizationGrantTypes(null).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasCountViolationsWithOneOfThemAs(violations, 2, "authorizationGrantTypes should be non-null");
    }

    @Test
    void authorizationGrantTypesShouldContainAtLeastOneElement() {
        CreateClientRequest request = aRequest().withAuthorizationGrantTypes(List.of()).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "authorizationGrantTypes should contain at least one element");
    }

    @Test
    void eachAuthorizationGrantTypeShouldBeNonNull() {
        CreateClientRequest request = aRequest().withAuthorizationGrantTypes(Collections.singletonList(null)).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "authorizationGrantType should be non-null");
    }

    @Test
    void authorizationGrantTypesShouldContainUniqueEntries() {
        List<AuthorizationGrantType> authorizationGrantTypes = List.of(AuthorizationGrantType.AUTHORIZATION_CODE, AuthorizationGrantType.AUTHORIZATION_CODE);
        CreateClientRequest request = aRequest().withAuthorizationGrantTypes(authorizationGrantTypes).build();
        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);
        hasOneViolationWithMessage(violations, "authorizationGrantTypes should be unique");
    }
}
