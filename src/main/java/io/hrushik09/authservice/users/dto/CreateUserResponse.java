package io.hrushik09.authservice.users.dto;

import io.hrushik09.authservice.users.User;

import java.util.List;

public record CreateUserResponse(
        Integer id,
        String username,
        List<UserAuthorityDTO> authorities
) {
    public static CreateUserResponse from(User user) {
        return new CreateUserResponse(user.getId(),
                user.getUsername(),
                UserAuthorityDTO.from(user.getAuthorities()));
    }
}
