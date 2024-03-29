package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.AuthenticationMethod;
import io.hrushik09.authservice.clients.AuthorizationGrantType;

import java.util.List;

public record CreateClientCommand(
        String pid,
        String clientId,
        String clientSecret,
        AuthenticationMethod authenticationMethod,
        List<String> scopes,
        List<String> redirectUris,
        List<AuthorizationGrantType> authorizationGrantTypes
) {
}
