package io.hrushik09.authservice.clients;

import java.time.Instant;

public class ClientRedirectUriBuilder {
    private Integer id = 45;
    private String value = "someRedirectUri";
    private Instant createdAt = Instant.parse("2024-03-05T01:11:12Z");
    private Instant updatedAt = Instant.parse("2024-03-06T01:11:12Z");

    private ClientRedirectUriBuilder() {
    }

    private ClientRedirectUriBuilder(ClientRedirectUriBuilder copy) {
        this.id = copy.id;
        this.value = copy.value;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static ClientRedirectUriBuilder aClientRedirectUri() {
        return new ClientRedirectUriBuilder();
    }

    public ClientRedirectUriBuilder but() {
        return new ClientRedirectUriBuilder(this);
    }

    public ClientRedirectUri build() {
        ClientRedirectUri clientRedirectUri = new ClientRedirectUri();
        clientRedirectUri.setId(id);
        clientRedirectUri.setValue(value);
        clientRedirectUri.setCreatedAt(createdAt);
        clientRedirectUri.setUpdatedAt(updatedAt);
        return clientRedirectUri;
    }

    public ClientRedirectUriBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public ClientRedirectUriBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public ClientRedirectUriBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ClientRedirectUriBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
