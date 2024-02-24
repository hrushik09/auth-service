package io.hrushik09.authservice.authorities.dto;

import java.time.Instant;

public record AuthorityDTO(
        Integer id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
}
