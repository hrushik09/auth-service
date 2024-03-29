package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.AuthorizationGrantType;
import io.hrushik09.authservice.clients.ClientAuthenticationMethod;

import java.util.List;

public record CreateClientCommand(
        String pid,
        String clientId,
        String clientSecret,
        ClientAuthenticationMethod clientAuthenticationMethod,
        List<String> scopes,
        List<String> redirectUris,
        List<AuthorizationGrantType> authorizationGrantTypes
) {
}
