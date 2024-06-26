package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.AuthenticationMethod;
import io.hrushik09.authservice.clients.AuthorizationGrantType;

import java.util.List;

public class CreateClientCommandBuilder {
    private String pid = "somePid";
    private String clientId = "someClientId";
    private String clientSecret = "someClientSecret";
    private AuthenticationMethod authenticationMethod = AuthenticationMethod.CLIENT_SECRET_BASIC;
    private List<String> scopes = List.of("OPENID");
    private List<String> redirectUris = List.of("someRedirectUri");
    private List<AuthorizationGrantType> authorizationGrantTypes = List.of(AuthorizationGrantType.AUTHORIZATION_CODE);

    private CreateClientCommandBuilder() {
    }

    private CreateClientCommandBuilder(CreateClientCommandBuilder copy) {
        this.pid = copy.pid;
        this.clientId = copy.clientId;
        this.clientSecret = copy.clientSecret;
        this.authenticationMethod = copy.authenticationMethod;
        this.scopes = copy.scopes;
        this.redirectUris = copy.redirectUris;
        this.authorizationGrantTypes = copy.authorizationGrantTypes;
    }

    public static CreateClientCommandBuilder aCommand() {
        return new CreateClientCommandBuilder();
    }

    public CreateClientCommandBuilder but() {
        return new CreateClientCommandBuilder(this);
    }

    public CreateClientCommand build() {
        return new CreateClientCommand(pid, clientId, clientSecret, authenticationMethod, scopes, redirectUris, authorizationGrantTypes);
    }

    public CreateClientCommandBuilder withPid(String pid) {
        this.pid = pid;
        return this;
    }

    public CreateClientCommandBuilder withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public CreateClientCommandBuilder withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public CreateClientCommandBuilder withAuthenticationMethod(AuthenticationMethod authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
        return this;
    }

    public CreateClientCommandBuilder withScopes(List<String> scopes) {
        this.scopes = scopes;
        return this;
    }

    public CreateClientCommandBuilder withRedirectUris(List<String> redirectUris) {
        this.redirectUris = redirectUris;
        return this;
    }

    public CreateClientCommandBuilder withAuthorizationGrantTypes(List<AuthorizationGrantType> authorizationGrantTypes) {
        this.authorizationGrantTypes = authorizationGrantTypes;
        return this;
    }
}
