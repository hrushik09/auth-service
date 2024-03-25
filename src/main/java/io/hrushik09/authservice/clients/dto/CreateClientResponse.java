package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.Client;
import io.hrushik09.authservice.clients.ClientAuthenticationMethod;

import java.util.List;

public record CreateClientResponse(
        Integer id,
        String pid,
        String clientId,
        ClientAuthenticationMethod clientAuthenticationMethod,
        List<String> scopes,
        String redirectUri,
        String authorizationGrantType
) {
    public static CreateClientResponse from(Client client) {
        return new CreateClientResponse(client.getId(), client.getPid(), client.getClientId(), client.getClientAuthenticationMethod(), List.of(client.getScope()), client.getRedirectUri(), client.getAuthorizationGrantType());
    }
}
