package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.ClientAuthenticationMethod;
import io.hrushik09.authservice.validation.ListContainsGivenStringConstraint;
import io.hrushik09.authservice.validation.UniqueEntriesConstraint;
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
        @NotNull(message = "clientAuthenticationMethod should be non-null")
        ClientAuthenticationMethod clientAuthenticationMethod,
        @NotNull(message = "scopes should be non-null")
        @Size(min = 1, message = "should contain at least one scope")
        @UniqueEntriesConstraint(message = "scopes should be unique")
        @ListContainsGivenStringConstraint(required = "OPENID", message = "scopes should contain OPENID scope")
        List<@NotBlank(message = "each scope should be non-blank") String> scopes,
        @NotNull(message = "redirectUris should be non-null")
        @Size(min = 1, message = "should contain at least one redirectUri")
        @UniqueEntriesConstraint(message = "redirectUris should be unique")
        List<@NotBlank(message = "redirectUri should be non-blank") String> redirectUris,
        @NotBlank(message = "authorizationGrantType should be non-blank")
        String authorizationGrantType
) {
}
