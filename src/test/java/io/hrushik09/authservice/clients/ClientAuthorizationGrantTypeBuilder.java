package io.hrushik09.authservice.clients;

import java.time.Instant;

public class ClientAuthorizationGrantTypeBuilder {
    private Integer id = 76;
    private AuthorizationGrantType value = AuthorizationGrantType.AUTHORIZATION_CODE;
    private Instant createdAt = Instant.parse("2024-03-07T01:21:12Z");
    private Instant updatedAt = Instant.parse("2024-03-08T01:51:09Z");

    private ClientAuthorizationGrantTypeBuilder() {
    }

    private ClientAuthorizationGrantTypeBuilder(ClientAuthorizationGrantTypeBuilder copy) {
        this.id = copy.id;
        this.value = copy.value;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static ClientAuthorizationGrantTypeBuilder aClientAuthorizationGrantType() {
        return new ClientAuthorizationGrantTypeBuilder();
    }

    public ClientAuthorizationGrantTypeBuilder but() {
        return new ClientAuthorizationGrantTypeBuilder(this);
    }

    public ClientAuthorizationGrantType build() {
        ClientAuthorizationGrantType clientAuthorizationGrantType = new ClientAuthorizationGrantType();
        clientAuthorizationGrantType.setId(id);
        clientAuthorizationGrantType.setValue(value);
        clientAuthorizationGrantType.setCreatedAt(createdAt);
        clientAuthorizationGrantType.setUpdatedAt(updatedAt);
        return clientAuthorizationGrantType;
    }

    public ClientAuthorizationGrantTypeBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public ClientAuthorizationGrantTypeBuilder withValue(AuthorizationGrantType value) {
        this.value = value;
        return this;
    }

    public ClientAuthorizationGrantTypeBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ClientAuthorizationGrantTypeBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
