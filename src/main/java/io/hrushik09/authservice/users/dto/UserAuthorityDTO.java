package io.hrushik09.authservice.users.dto;

import io.hrushik09.authservice.authorities.Authority;

import java.util.List;
import java.util.Set;

public record UserAuthorityDTO(
        Integer id,
        String name
) {
    public static List<UserAuthorityDTO> from(Set<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new UserAuthorityDTO(authority.getId(), authority.getName()))
                .toList();
    }
}
