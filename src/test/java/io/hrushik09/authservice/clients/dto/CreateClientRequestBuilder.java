package io.hrushik09.authservice.clients.dto;

import io.hrushik09.authservice.clients.AuthorizationGrantType;
import io.hrushik09.authservice.clients.ClientAuthenticationMethod;

import java.util.List;

public class CreateClientRequestBuilder {
    private String pid = "somePid";
    private String clientId = "someClientId";
    private String clientSecret = "someClientSecret";
    private ClientAuthenticationMethod clientAuthenticationMethod = ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
    private List<String> scopes = List.of("OPENID");
    private List<String> redirectUris = List.of("someRedirectUri");
    private List<AuthorizationGrantType> authorizationGrantTypes = List.of(AuthorizationGrantType.AUTHORIZATION_CODE);

    private CreateClientRequestBuilder() {
    }

    private CreateClientRequestBuilder(CreateClientRequestBuilder copy) {
        this.pid = copy.pid;
        this.clientId = copy.clientId;
        this.clientSecret = copy.clientSecret;
        this.clientAuthenticationMethod = copy.clientAuthenticationMethod;
        this.scopes = copy.scopes;
        this.redirectUris = copy.redirectUris;
        this.authorizationGrantTypes = copy.authorizationGrantTypes;
    }

    public static CreateClientRequestBuilder aRequest() {
        return new CreateClientRequestBuilder();
    }

    public CreateClientRequestBuilder but() {
        return new CreateClientRequestBuilder(this);
    }

    public CreateClientRequest build() {
        return new CreateClientRequest(pid, clientId, clientSecret, clientAuthenticationMethod, scopes, redirectUris, authorizationGrantTypes);
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

    public CreateClientRequestBuilder withRedirectUris(List<String> redirectUris) {
        this.redirectUris = redirectUris;
        return this;
    }

    public CreateClientRequestBuilder withAuthorizationGrantTypes(List<AuthorizationGrantType> authorizationGrantTypes) {
        this.authorizationGrantTypes = authorizationGrantTypes;
        return this;
    }
}
