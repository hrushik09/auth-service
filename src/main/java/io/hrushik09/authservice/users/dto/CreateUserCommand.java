package io.hrushik09.authservice.users.dto;

import java.util.List;

public record CreateUserCommand(
        String username,
        String password,
        List<String> authorities
) {
}
