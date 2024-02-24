package io.hrushik09.authservice.authorities;

import jakarta.validation.constraints.NotBlank;

public record CreateAuthorityRequest(
        @NotBlank(message = "name should be non-empty")
        String name
) {
}
