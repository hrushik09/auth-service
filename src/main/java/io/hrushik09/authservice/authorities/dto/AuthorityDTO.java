package io.hrushik09.authservice.authorities.dto;

import io.hrushik09.authservice.authorities.Authority;

import java.time.Instant;

public record AuthorityDTO(
        Integer id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
    public static AuthorityDTO from(Authority authority) {
        return new AuthorityDTO(authority.getId(),
                authority.getName(),
                authority.getCreatedAt(),
                authority.getUpdatedAt());
    }
}
