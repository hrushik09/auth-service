package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.ClientAuthenticationMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateClientRequest(
        @NotBlank(message = "id should be non-blank")
        String id,
        @NotBlank(message = "clientId should be non-blank")
        String clientId,
        @NotBlank(message = "clientSecret should be non-blank")
        String clientSecret,
        @NotNull(message = "clientAuthenticationMethod should be non-null")
        ClientAuthenticationMethod clientAuthenticationMethod,
        @NotBlank(message = "scope should be non-blank")
        String scope,
        @NotBlank(message = "redirectUri should be non-blank")
        String redirectUri,
        @NotBlank(message = "authorizationGrantType should be non-blank")
        String authorizationGrantType
) {
}
