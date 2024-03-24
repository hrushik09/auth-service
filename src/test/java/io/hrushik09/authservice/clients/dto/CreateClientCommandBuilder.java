package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.ClientAuthenticationMethod;

public class CreateClientCommandBuilder {
    private String pid = "somePid";
    private String clientId = "someClientId";
    private String clientSecret = "someClientSecret";
    private ClientAuthenticationMethod clientAuthenticationMethod = ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
    private String scope = "someScope";
    private String redirectUri = "someRedirectUri";
    private String authorizationGrantType = "someAuthorizationGrantType";

    private CreateClientCommandBuilder() {
    }

    private CreateClientCommandBuilder(CreateClientCommandBuilder copy) {
        this.pid = copy.pid;
        this.clientId = copy.clientId;
        this.clientSecret = copy.clientSecret;
        this.clientAuthenticationMethod = copy.clientAuthenticationMethod;
        this.scope = copy.scope;
        this.redirectUri = copy.redirectUri;
        this.authorizationGrantType = copy.authorizationGrantType;
    }

    public static CreateClientCommandBuilder aCommand() {
        return new CreateClientCommandBuilder();
    }

    public CreateClientCommandBuilder but() {
        return new CreateClientCommandBuilder(this);
    }

    public CreateClientCommand build() {
        return new CreateClientCommand(pid, clientId, clientSecret, clientAuthenticationMethod, scope, redirectUri, authorizationGrantType);
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

    public CreateClientCommandBuilder withClientAuthenticationMethod(ClientAuthenticationMethod clientAuthenticationMethod) {
        this.clientAuthenticationMethod = clientAuthenticationMethod;
        return this;
    }

    public CreateClientCommandBuilder withScope(String scope) {
        this.scope = scope;
        return this;
    }

    public CreateClientCommandBuilder withRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public CreateClientCommandBuilder withAuthorizationGrantType(String authorizationGrantType) {
        this.authorizationGrantType = authorizationGrantType;
        return this;
    }
}