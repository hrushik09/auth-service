package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.Client;
import io.hrushik09.authservice.clients.ClientAuthenticationMethod;
import io.hrushik09.authservice.clients.ClientScope;

import java.util.List;

public record CreateClientResponse(
        Integer id,
        String pid,
        String clientId,
        ClientAuthenticationMethod clientAuthenticationMethod,
        List<String> scopes,
        List<String> redirectUris,
        String authorizationGrantType
) {
    public static CreateClientResponse from(Client client) {
        List<String> scopes = client.getClientScopes().stream()
                .map(ClientScope::getValue)
                .toList();
        return new CreateClientResponse(client.getId(), client.getPid(), client.getClientId(), client.getClientAuthenticationMethod(), scopes, List.of(client.getRedirectUri()), client.getAuthorizationGrantType());
    }
}
