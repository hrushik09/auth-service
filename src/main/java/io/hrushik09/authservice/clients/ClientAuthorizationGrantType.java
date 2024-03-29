package io.hrushik09.authservice.clients;

import java.time.Instant;

public class ClientAuthorizationGrantType {
    private Integer id;
    private AuthorizationGrantType value;
    private Instant createdAt;
    private Instant updatedAt;

    public ClientAuthorizationGrantType() {
    }

    public ClientAuthorizationGrantType(AuthorizationGrantType value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AuthorizationGrantType getValue() {
        return value;
    }

    public void setValue(AuthorizationGrantType value) {
        this.value = value;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
