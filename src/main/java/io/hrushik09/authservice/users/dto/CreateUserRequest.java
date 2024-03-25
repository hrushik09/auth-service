package io.hrushik09.authservice.users.dto;

import io.hrushik09.authservice.validation.UniqueEntriesConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateUserRequest(
        @NotBlank(message = "username should be non-blank")
        String username,
        @NotBlank(message = "password should be non-blank")
        String password,
        @Size(min = 1, message = "should have at least one authority")
        @UniqueEntriesConstraint(message = "authorities should be unique")
        List<@NotBlank(message = "each authority name should be non-blank") String> authorities
) {
}
