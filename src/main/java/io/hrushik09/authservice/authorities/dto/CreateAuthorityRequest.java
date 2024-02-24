package io.hrushik09.authservice.authorities.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAuthorityRequest(
        @NotBlank(message = "name should be non-empty")
        String name
) {
}
