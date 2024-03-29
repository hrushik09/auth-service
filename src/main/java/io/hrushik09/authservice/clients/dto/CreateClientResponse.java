package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.*;

import java.util.List;

public record CreateClientResponse(
        Integer id,
        String pid,
        String clientId,
        ClientAuthenticationMethod clientAuthenticationMethod,
        List<String> scopes,
        List<String> redirectUris,
        List<AuthorizationGrantType> authorizationGrantTypes
) {
    public static CreateClientResponse from(Client client) {
        List<String> scopes = client.getClientScopes().stream()
                .map(ClientScope::getValue)
                .toList();
        List<String> redirectUris = client.getClientRedirectUris().stream()
                .map(ClientRedirectUri::getValue)
                .toList();
        return new CreateClientResponse(client.getId(), client.getPid(), client.getClientId(), client.getClientAuthenticationMethod(), scopes, redirectUris, List.of(AuthorizationGrantType.valueOf(client.getAuthorizationGrantType())));
    }
}
