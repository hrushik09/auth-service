package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.ClientAuthenticationMethod;

public record CreateClientResponse(
        String id,
        String clientId,
        ClientAuthenticationMethod clientAuthenticationMethod,
        String scope,
        String redirectUri,
        String authorizationGrantType
) {
}
