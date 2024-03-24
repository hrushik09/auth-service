package io.hrushik09.authservice.users;

import io.hrushik09.authservice.authorities.AuthorityBuilder;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserBuilder {
    private Integer id = 65;
    private String username = "randomUsername";
    private String password = "randomPassword";
    private Set<AuthorityBuilder> authorityBuilders = new HashSet<>();
    private Instant createdAt = Instant.parse("2023-03-03T03:03:03Z");
    private Instant updatedAt = Instant.parse("2023-04-04T04:04:04Z");

    private UserBuilder() {
    }

    private UserBuilder(UserBuilder copy) {
        this.id = copy.id;
        this.username = copy.username;
        this.password = copy.password;
        this.authorityBuilders = copy.authorityBuilders;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder but() {
        return new UserBuilder(this);
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setAuthorities(authorityBuilders.stream()
                .map(AuthorityBuilder::build)
                .collect(Collectors.toUnmodifiableSet()));
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);
        return user;
    }

    public UserBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder with(AuthorityBuilder authorityBuilder) {
        this.authorityBuilders.add(authorityBuilder);
        return this;
    }

    public UserBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
