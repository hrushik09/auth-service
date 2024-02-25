package io.hrushik09.authservice.users;

import io.hrushik09.authservice.users.dto.CreateUserCommand;
import io.hrushik09.authservice.users.exceptions.UsernameAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static io.hrushik09.authservice.users.UserBuilder.aUser;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Nested
    class Create {
        @Test
        void shouldThrowWhenUsernameAlreadyExists() {
            String duplicateUsername = "duplicateUsername";
            when(userRepository.findByUsername(duplicateUsername))
                    .thenReturn(Optional.of(aUser().withUsername(duplicateUsername).build()));

            CreateUserCommand cmd = new CreateUserCommand(duplicateUsername, "doesnt-matter", List.of("randomName"));
            assertThatThrownBy(() -> userService.create(cmd))
                    .isInstanceOf(UsernameAlreadyExistsException.class)
                    .hasMessage("User with username " + duplicateUsername + " already exists");
        }
    }
}