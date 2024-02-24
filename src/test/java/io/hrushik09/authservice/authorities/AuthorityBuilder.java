package io.hrushik09.authservice.authorities;

import java.time.Instant;

public class AuthorityBuilder {
    private Integer id = 54;
    private String name = "someDomain:someAuthority";
    private Instant createdAt = Instant.parse("2023-01-01T12:12:12Z");
    private Instant updatedAt = Instant.parse("2023-01-02T12:12:12Z");

    private AuthorityBuilder() {
    }

    private AuthorityBuilder(AuthorityBuilder copy) {
        this.id = copy.id;
        this.name = copy.name;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static AuthorityBuilder anAuthority() {
        return new AuthorityBuilder();
    }

    public AuthorityBuilder but() {
        return new AuthorityBuilder(this);
    }

    public AuthorityBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public AuthorityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public AuthorityBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AuthorityBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Authority build() {
        Authority authority = new Authority();
        authority.setId(id);
        authority.setName(name);
        authority.setCreatedAt(createdAt);
        authority.setUpdatedAt(updatedAt);
        return authority;
    }
}
