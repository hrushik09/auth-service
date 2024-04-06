package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.AuthenticationMethod;
import io.hrushik09.authservice.clients.AuthorizationGrantType;
import io.hrushik09.authservice.clients.validation.ValueOfEnumConstraint;
import io.hrushik09.authservice.validation.ListContainsGivenStringConstraint;
import io.hrushik09.authservice.validation.ListContainsUniqueStringsConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateClientRequest(
        @NotBlank(message = "pid should be non-blank")
        String pid,
        @NotBlank(message = "clientId should be non-blank")
        String clientId,
        @NotBlank(message = "clientSecret should be non-blank")
        String clientSecret,
        @NotNull(message = "authenticationMethod should be non-null")
        @ValueOfEnumConstraint(enumClass = AuthenticationMethod.class, message = "authenticationMethod should be valid")
        String authenticationMethod,
        @NotNull(message = "scopes should be non-null")
        @Size(min = 1, message = "should contain at least one scope")
        @ListContainsUniqueStringsConstraint(message = "scopes should be unique")
        @ListContainsGivenStringConstraint(required = "OPENID", message = "scopes should contain OPENID scope")
        List<@NotBlank(message = "each scope should be non-blank") String> scopes,
        @NotNull(message = "redirectUris should be non-null")
        @Size(min = 1, message = "should contain at least one redirectUri")
        @ListContainsUniqueStringsConstraint(message = "redirectUris should be unique")
        List<@NotBlank(message = "redirectUri should be non-blank") String> redirectUris,
        @NotNull(message = "authorizationGrantTypes should be non-null")
        @Size(min = 1, message = "authorizationGrantTypes should contain at least one element")
        @ListContainsUniqueStringsConstraint(message = "authorizationGrantTypes should be unique")
        List<@NotNull(message = "authorizationGrantType should be non-null") @ValueOfEnumConstraint(enumClass = AuthorizationGrantType.class, message = "authorizationGrantType should be valid") String> authorizationGrantTypes
) {
}
