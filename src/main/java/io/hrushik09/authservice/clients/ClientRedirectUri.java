package io.hrushik09.authservice.clients;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "client_redirect_uris",
        uniqueConstraints = @UniqueConstraint(name = "UK_client_redirect_uris_value_client_id", columnNames = {"value", "client_id"})
)
public class ClientRedirectUri {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String value;
    @Column(nullable = false, insertable = false, updatable = false)
    private Instant createdAt;
    @Column(nullable = false, insertable = false, updatable = false)
    private Instant updatedAt;

    public ClientRedirectUri() {
    }

    public ClientRedirectUri(String value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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
