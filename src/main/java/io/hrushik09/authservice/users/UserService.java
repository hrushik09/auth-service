package io.hrushik09.authservice.users;

import io.hrushik09.authservice.users.dto.CreateUserCommand;
import io.hrushik09.authservice.users.dto.CreateUserResponse;
import io.hrushik09.authservice.users.exceptions.UsernameAlreadyExistsException;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CreateUserResponse create(CreateUserCommand cmd) {
        userRepository.findByUsername(cmd.username())
                .ifPresent(user -> {
                    throw new UsernameAlreadyExistsException(cmd.username());
                });
        return null;
    }
}
