package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.Client;
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
    public static CreateClientResponse from(Client client) {
        return new CreateClientResponse(client.getId(), client.getPid(), client.getClientId(), client.getClientAuthenticationMethod(), client.getScope(), client.getRedirectUri(), client.getAuthorizationGrantType());
    }
}
