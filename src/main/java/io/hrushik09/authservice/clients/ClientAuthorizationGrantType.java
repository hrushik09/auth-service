package io.hrushik09.authservice.clients;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "client_authorization_grant_types",
        uniqueConstraints = @UniqueConstraint(name = "UK_client_authorization_grant_types_value_client_id", columnNames = {"value", "client_id"})
)
public class ClientAuthorizationGrantType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AuthorizationGrantType value;
    @Column(nullable = false, insertable = false, updatable = false)
    private Instant createdAt;
    @Column(nullable = false, insertable = false, updatable = false)
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
