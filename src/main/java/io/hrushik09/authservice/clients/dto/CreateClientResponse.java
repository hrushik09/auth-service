package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.ClientAuthenticationMethod;

public record CreateClientResponse(
        Integer id,
        String pid,
        String clientId,
        ClientAuthenticationMethod clientAuthenticationMethod,
        String scope,
        String redirectUri,
        String authorizationGrantType
) {
}
