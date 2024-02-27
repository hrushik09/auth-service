package io.hrushik09.authservice.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateUserRequest(
        @NotBlank(message = "username should be non-empty")
        String username,
        @NotBlank(message = "password should be non-empty")
        String password,
        @Size(min = 1, message = "should have at least one authority")
        List<@NotBlank(message = "each authority name should be non-empty") String> authorities
) {
}
