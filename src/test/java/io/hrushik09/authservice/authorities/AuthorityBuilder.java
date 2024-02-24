package io.hrushik09.authservice.authorities;

public class AuthorityBuilder {
    private Integer id = 54;
    private String name = "someDomain:someAuthority";

    private AuthorityBuilder() {
    }

    private AuthorityBuilder(AuthorityBuilder copy) {
        this.id = copy.id;
        this.name = copy.name;
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

    public Authority build() {
        Authority authority = new Authority();
        authority.setId(id);
        authority.setName(name);
        return authority;
    }
}
