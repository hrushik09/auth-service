package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.ClientAuthenticationMethod;

public record CreateClientCommand(
        String id,
        String clientId,
        String clientSecret,
        ClientAuthenticationMethod clientAuthenticationMethod,
        String scope,
        String redirectUri,
        String authorizationGrantType
) {
}
