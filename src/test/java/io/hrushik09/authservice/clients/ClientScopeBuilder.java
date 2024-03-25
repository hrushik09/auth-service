package io.hrushik09.authservice.clients;

import java.time.Instant;

public class ClientScopeBuilder {
    private Integer id = 45;
    private String value = "someScope";
    private Instant createdAt = Instant.parse("2024-03-04T01:11:12Z");
    private Instant updatedAt = Instant.parse("2024-03-05T01:11:12Z");

    private ClientScopeBuilder() {
    }

    private ClientScopeBuilder(ClientScopeBuilder copy) {
        this.id = copy.id;
        this.value = copy.value;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static ClientScopeBuilder aClientScope() {
        return new ClientScopeBuilder();
    }

    public ClientScopeBuilder but() {
        return new ClientScopeBuilder(this);
    }

    public ClientScope build() {
        ClientScope clientScope = new ClientScope();
        clientScope.setId(id);
        clientScope.setValue(value);
        clientScope.setCreatedAt(createdAt);
        clientScope.setUpdatedAt(updatedAt);
        return clientScope;
    }

    public ClientScopeBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public ClientScopeBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public ClientScopeBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ClientScopeBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
