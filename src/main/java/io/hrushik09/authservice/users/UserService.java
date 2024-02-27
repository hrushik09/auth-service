package io.hrushik09.authservice.users;

import io.hrushik09.authservice.authorities.Authority;
import io.hrushik09.authservice.authorities.AuthorityService;
import io.hrushik09.authservice.users.dto.CreateUserCommand;
import io.hrushik09.authservice.users.dto.CreateUserResponse;
import io.hrushik09.authservice.users.exceptions.UsernameAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthorityService authorityService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CreateUserResponse create(CreateUserCommand cmd) {
        userRepository.findByUsername(cmd.username())
                .ifPresent(user -> {
                    throw new UsernameAlreadyExistsException(cmd.username());
                });

        Set<Authority> authorities = cmd.authorities().stream()
                .map(authorityService::fetchByName)
                .collect(Collectors.toSet());

        User user = new User();
        user.setUsername(cmd.username());
        user.setPassword(passwordEncoder.encode(cmd.password()));
        user.setAuthorities(authorities);
        User saved = userRepository.save(user);
        return CreateUserResponse.from(saved);
    }
}
