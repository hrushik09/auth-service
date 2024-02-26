package io.hrushik09.authservice.users;

import io.hrushik09.authservice.authorities.Authority;
import io.hrushik09.authservice.authorities.AuthorityService;
import io.hrushik09.authservice.users.dto.CreateUserCommand;
import io.hrushik09.authservice.users.dto.CreateUserResponse;
import io.hrushik09.authservice.users.exceptions.UsernameAlreadyExistsException;

import java.util.Set;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;
    private final AuthorityService authorityService;

    public UserService(UserRepository userRepository, AuthorityService authorityService) {
        this.userRepository = userRepository;
        this.authorityService = authorityService;
    }

    public CreateUserResponse create(CreateUserCommand cmd) {
        userRepository.findByUsername(cmd.username())
                .ifPresent(user -> {
                    throw new UsernameAlreadyExistsException(cmd.username());
                });

        Set<Authority> authorities = cmd.authorities().stream()
                .map(authorityService::fetchByName)
                .collect(Collectors.toSet());
        return null;
    }
}
