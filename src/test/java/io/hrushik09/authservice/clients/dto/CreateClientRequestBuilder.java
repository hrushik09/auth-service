package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.ClientAuthenticationMethod;

import java.util.List;

public class CreateClientRequestBuilder {
    private String pid = "somePid";
    private String clientId = "someClientId";
    private String clientSecret = "someClientSecret";
    private ClientAuthenticationMethod clientAuthenticationMethod = ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
    private List<String> scopes = List.of("OPENID");
    private String redirectUri = "someRedirectUri";
    private String authorizationGrantType = "someAuthorizationGrantType";

    private CreateClientRequestBuilder() {
    }

    private CreateClientRequestBuilder(CreateClientRequestBuilder copy) {
        this.pid = copy.pid;
        this.clientId = copy.clientId;
        this.clientSecret = copy.clientSecret;
        this.clientAuthenticationMethod = copy.clientAuthenticationMethod;
        this.scopes = copy.scopes;
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
        return new CreateClientRequest(pid, clientId, clientSecret, clientAuthenticationMethod, scopes, redirectUri, authorizationGrantType);
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

    public CreateClientRequestBuilder withScopes(List<String> scopes) {
        this.scopes = scopes;
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
