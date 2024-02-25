package io.hrushik09.authservice.users.dto;

import java.util.List;

public record CreateUserResponse(
        Integer id,
        String username,
        List<UserAuthorityDTO> authorities
) {
}
