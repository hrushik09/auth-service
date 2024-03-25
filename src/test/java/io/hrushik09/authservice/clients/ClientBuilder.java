package io.hrushik09.authservice.clients;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ClientBuilder {
    private Integer id = 23;
    private String pid = "somePid";
    private String clientId = "someClientId";
    private String clientSecret = "someSecret";
    private ClientAuthenticationMethod clientAuthenticationMethod = ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
    private List<ClientScopeBuilder> clientScopeBuilderList = new ArrayList<>();
    private String redirectUri = "someRedirectUri";
    private String authorizationGrantType = "someAuthorizationGrantType";
    private Instant createdAt = Instant.parse("2024-01-12T04:03:03Z");
    private Instant updatedAt = Instant.parse("2024-01-12T03:04:04Z");

    private ClientBuilder() {
    }

    private ClientBuilder(ClientBuilder copy) {
        this.id = copy.id;
        this.pid = copy.pid;
        this.clientId = copy.clientId;
        this.clientSecret = copy.clientSecret;
        this.clientAuthenticationMethod = copy.clientAuthenticationMethod;
        this.clientScopeBuilderList = copy.clientScopeBuilderList;
        this.redirectUri = copy.redirectUri;
        this.authorizationGrantType = copy.authorizationGrantType;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static ClientBuilder aClient() {
        return new ClientBuilder();
    }

    public ClientBuilder but() {
        return new ClientBuilder(this);
    }

    public Client build() {
        Client client = new Client();
        client.setId(id);
        client.setPid(pid);
        client.setClientId(clientId);
        client.setClientSecret(clientSecret);
        client.setClientAuthenticationMethod(clientAuthenticationMethod);
        List<ClientScope> clientScopes = clientScopeBuilderList.stream()
                .map(ClientScopeBuilder::build)
                .toList();
        client.setClientScopes(clientScopes);
        client.setRedirectUri(redirectUri);
        client.setAuthorizationGrantType(authorizationGrantType);
        client.setCreatedAt(createdAt);
        client.setUpdatedAt(updatedAt);
        return client;
    }

    public ClientBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public ClientBuilder withPid(String pid) {
        this.pid = pid;
        return this;
    }

    public ClientBuilder withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public ClientBuilder withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public ClientBuilder withClientAuthenticationMethod(ClientAuthenticationMethod clientAuthenticationMethod) {
        this.clientAuthenticationMethod = clientAuthenticationMethod;
        return this;
    }

    public ClientBuilder with(ClientScopeBuilder clientScopeBuilder) {
        this.clientScopeBuilderList.add(clientScopeBuilder);
        return this;
    }

    public ClientBuilder withRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public ClientBuilder withAuthorizationGrantType(String authorizationGrantType) {
        this.authorizationGrantType = authorizationGrantType;
        return this;
    }

    public ClientBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ClientBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
