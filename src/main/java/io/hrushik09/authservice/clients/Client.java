package io.hrushik09.authservice.clients;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String pid;
    @Column(nullable = false, unique = true)
    private String clientId;
    @Column(nullable = false)
    private String clientSecret;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AuthenticationMethod authenticationMethod;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "client_id", nullable = false)
    private List<ClientScope> clientScopes = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "client_id", nullable = false)
    private List<ClientRedirectUri> clientRedirectUris;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "client_id", nullable = false)
    private List<ClientAuthorizationGrantType> clientAuthorizationGrantTypes;
    @Column(nullable = false, insertable = false, updatable = false)
    private Instant createdAt;
    @Column(nullable = false, insertable = false, updatable = false)
    private Instant updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public AuthenticationMethod getAuthenticationMethod() {
        return authenticationMethod;
    }

    public void setAuthenticationMethod(AuthenticationMethod authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    public List<ClientScope> getClientScopes() {
        return clientScopes;
    }

    public void setClientScopes(List<ClientScope> clientScopes) {
        this.clientScopes = clientScopes;
    }

    public List<ClientRedirectUri> getClientRedirectUris() {
        return clientRedirectUris;
    }

    public void setClientRedirectUris(List<ClientRedirectUri> clientRedirectUris) {
        this.clientRedirectUris = clientRedirectUris;
    }

    public List<ClientAuthorizationGrantType> getClientAuthorizationGrantTypes() {
        return clientAuthorizationGrantTypes;
    }

    public void setClientAuthorizationGrantTypes(List<ClientAuthorizationGrantType> clientAuthorizationGrantTypes) {
        this.clientAuthorizationGrantTypes = clientAuthorizationGrantTypes;
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
