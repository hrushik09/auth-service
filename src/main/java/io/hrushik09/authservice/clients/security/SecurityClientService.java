package io.hrushik09.authservice.clients.security;

import io.hrushik09.authservice.clients.*;
import io.hrushik09.authservice.clients.dto.CreateClientCommand;
import io.hrushik09.authservice.clients.exceptions.ClientConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SecurityClientService implements RegisteredClientRepository {
    private static final Logger logger = LoggerFactory.getLogger(SecurityClientService.class);
    private final ClientService clientService;

    public SecurityClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    private static CreateClientCommand from(RegisteredClient registeredClient) throws ClientConversionException {
        try {
            AuthenticationMethod authenticationMethod = registeredClient.getClientAuthenticationMethods().stream().findFirst()
                    .map(am -> AuthenticationMethod.valueOf(am.getValue()))
                    .orElseThrow();
            List<String> scopes = List.copyOf(registeredClient.getScopes());
            List<String> redirectUris = List.copyOf(registeredClient.getRedirectUris());
            List<AuthorizationGrantType> authorizationGrantTypes = registeredClient.getAuthorizationGrantTypes().stream()
                    .map(authorizationGrantType -> AuthorizationGrantType.valueOf(authorizationGrantType.getValue()))
                    .toList();
            return new CreateClientCommand(registeredClient.getId(), registeredClient.getClientId(), registeredClient.getClientSecret(),
                    authenticationMethod, scopes, redirectUris, authorizationGrantTypes);
        } catch (Exception e) {
            String message = "exception while converting registeredClient %s: %s".formatted(registeredClient.getClientId(), e.getMessage());
            logger.error(message);
            throw new ClientConversionException(message);
        }
    }

    private static RegisteredClient from(Client client) {
        return RegisteredClient.withId(client.getPid())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientAuthenticationMethod(getClientAuthenticationMethod(client))
                .scopes(scopes ->
                        client.getClientScopes().forEach(scope ->
                                scopes.add(getScope(scope))))
                .redirectUris(redirectUris ->
                        client.getClientRedirectUris().forEach(redirectUri ->
                                redirectUris.add(redirectUri.getValue())))
                .authorizationGrantTypes(grantTypes ->
                        client.getClientAuthorizationGrantTypes().forEach(grantType ->
                                grantTypes.add(getAuthorizationGrantType(grantType))))
                .tokenSettings(TokenSettings.builder()
                        .authorizationCodeTimeToLive(Duration.ofMinutes(30))
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .refreshTokenTimeToLive(Duration.ofMinutes(300))
                        .build())
                .build();
    }

    private static String getScope(ClientScope scope) {
        if ("OPENID".equals(scope.getValue())) {
            return "openid";
        } else {
            return scope.getValue();
        }
    }

    private static ClientAuthenticationMethod getClientAuthenticationMethod(Client client) {
        if (client.getAuthenticationMethod().equals(AuthenticationMethod.CLIENT_SECRET_BASIC)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
        } else {
            return new ClientAuthenticationMethod(client.getAuthenticationMethod().name());
        }
    }

    private static org.springframework.security.oauth2.core.AuthorizationGrantType getAuthorizationGrantType(ClientAuthorizationGrantType grantType) {
        if (grantType.getValue().equals(AuthorizationGrantType.AUTHORIZATION_CODE)) {
            return org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (grantType.getValue().equals(AuthorizationGrantType.REFRESH_TOKEN)) {
            return org.springframework.security.oauth2.core.AuthorizationGrantType.REFRESH_TOKEN;
        } else {
            return new org.springframework.security.oauth2.core.AuthorizationGrantType(grantType.getValue().name());
        }
    }

    @Override
    @Transactional
    public void save(RegisteredClient registeredClient) {
        try {
            CreateClientCommand cmd = from(registeredClient);
            clientService.create(cmd);
        } catch (ClientConversionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RegisteredClient findById(String id) {
        Client client = clientService.findByPid(id);
        return from(client);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Client client = clientService.findByClientId(clientId);
        return from(client);
    }
}
