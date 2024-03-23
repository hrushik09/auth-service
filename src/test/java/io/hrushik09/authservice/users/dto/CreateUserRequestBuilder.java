package io.hrushik09.authservice.users.dto;

import java.util.List;

public class CreateUserRequestBuilder {
    private String username = "randomUsername";
    private String password = "randomPassword";
    private List<String> authorities = List.of("api:randomAuthority");

    private CreateUserRequestBuilder() {
    }

    private CreateUserRequestBuilder(CreateUserRequestBuilder copy) {
        this.username = copy.username;
        this.password = copy.password;
        this.authorities = copy.authorities;
    }

    public static CreateUserRequestBuilder aRequest() {
        return new CreateUserRequestBuilder();
    }

    public CreateUserRequestBuilder but() {
        return new CreateUserRequestBuilder(this);
    }

    public CreateUserRequest build() {
        return new CreateUserRequest(username, password, authorities);
    }

    public CreateUserRequestBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public CreateUserRequestBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public CreateUserRequestBuilder withAuthorities(List<String> authorities) {
        this.authorities = authorities;
        return this;
    }
}
