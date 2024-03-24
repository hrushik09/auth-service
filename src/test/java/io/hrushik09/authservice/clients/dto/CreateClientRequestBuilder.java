package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.ClientAuthenticationMethod;

public class CreateClientRequestBuilder {
    private String pid = "somePid";
    private String clientId = "someClientId";
    private String clientSecret = "someClientSecret";
    private ClientAuthenticationMethod clientAuthenticationMethod = ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
    private String scope = "someScope";
    private String redirectUri = "someRedirectUri";
    private String authorizationGrantType = "someAuthorizationGrantType";

    private CreateClientRequestBuilder() {
    }

    private CreateClientRequestBuilder(CreateClientRequestBuilder copy) {
        this.pid = copy.pid;
        this.clientId = copy.clientId;
        this.clientSecret = copy.clientSecret;
        this.clientAuthenticationMethod = copy.clientAuthenticationMethod;
        this.scope = copy.scope;
        this.redirectUri = copy.redirectUri;
        this.authorizationGrantType = copy.authorizationGrantType;
    }

    public static CreateClientRequestBuilder aRequest() {
        return new CreateClientRequestBuilder();
    }

    public CreateClientRequestBuilder but() {
        return new CreateClientRequestBuilder(this);
    }

    public CreateClientRequest build() {
        return new CreateClientRequest(pid, clientId, clientSecret, clientAuthenticationMethod, scope, redirectUri, authorizationGrantType);
    }

    public CreateClientRequestBuilder withPid(String pid) {
        this.pid = pid;
        return this;
    }

    public CreateClientRequestBuilder withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public CreateClientRequestBuilder withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public CreateClientRequestBuilder withClientAuthenticationMethod(ClientAuthenticationMethod clientAuthenticationMethod) {
        this.clientAuthenticationMethod = clientAuthenticationMethod;
        return this;
    }

    public CreateClientRequestBuilder withScope(String scope) {
        this.scope = scope;
        return this;
    }

    public CreateClientRequestBuilder withRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public CreateClientRequestBuilder withAuthorizationGrantType(String authorizationGrantType) {
        this.authorizationGrantType = authorizationGrantType;
        return this;
    }
}
