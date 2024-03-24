package io.hrushik09.authservice.clients;

public class ClientBuilder {
    private Integer id = 23;
    private String pid = "somePid";
    private String clientId = "someClientId";
    private String clientSecret = "someSecret";
    private ClientAuthenticationMethod clientAuthenticationMethod = ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
    private String scope = "someScope";
    private String redirectUri = "someRedirectUri";
    private String authorizationGrantType = "someAuthorizationGrantType";

    private ClientBuilder() {
    }

    private ClientBuilder(ClientBuilder copy) {
        this.id = copy.id;
        this.pid = copy.pid;
        this.clientId = copy.clientId;
        this.clientSecret = copy.clientSecret;
        this.clientAuthenticationMethod = copy.clientAuthenticationMethod;
        this.scope = copy.scope;
        this.redirectUri = copy.redirectUri;
        this.authorizationGrantType = copy.authorizationGrantType;
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
        client.setScope(scope);
        client.setRedirectUri(redirectUri);
        client.setAuthorizationGrantType(authorizationGrantType);
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

    public ClientBuilder withScope(String scope) {
        this.scope = scope;
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
}
