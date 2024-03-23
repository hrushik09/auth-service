package io.hrushik09.authservice.users;

import io.hrushik09.authservice.authorities.AuthorityService;
import io.hrushik09.authservice.authorities.exceptions.AuthorityDoesNotExist;
import io.hrushik09.authservice.users.dto.CreateUserCommand;
import io.hrushik09.authservice.users.dto.CreateUserResponse;
import io.hrushik09.authservice.users.exceptions.UsernameAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static io.hrushik09.authservice.authorities.AuthorityBuilder.anAuthority;
import static io.hrushik09.authservice.users.UserBuilder.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthorityService authorityService;

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, authorityService, passwordEncoder);
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

        @Test
        void shouldThrowWhenAuthorityDoesNotExist() {
            String newAuthority = "newAuthority";
            when(authorityService.fetchByName(newAuthority))
                    .thenReturn(anAuthority().withName(newAuthority).build());
            String doesNotExistAuthority = "doesNotExistAuthority";
            when(authorityService.fetchByName(doesNotExistAuthority))
                    .thenThrow(new AuthorityDoesNotExist(doesNotExistAuthority));

            CreateUserCommand cmd = new CreateUserCommand("doesnt-matter", "doesnt-matter", List.of(newAuthority, doesNotExistAuthority));
            assertThatThrownBy(() -> userService.create(cmd))
                    .isInstanceOf(AuthorityDoesNotExist.class)
                    .hasMessage("Authority with name " + doesNotExistAuthority + " does not exist");
        }

        @Test
        void shouldSaveUsingRepositoryWhenCreatingUser() {
            when(authorityService.fetchByName("api:delete"))
                    .thenReturn(anAuthority().withName("api:delete").build());
            when(authorityService.fetchByName("api:update"))
                    .thenReturn(anAuthority().withName("api:update").build());
            String username = "User 34";
            UserBuilder userBuilder = aUser().withUsername(username).withPassword("sdfj*34j")
                    .with(anAuthority().withName("api:delete"))
                    .with(anAuthority().withName("api:update"));
            when(userRepository.save(any(User.class)))
                    .thenReturn(userBuilder.build());

            CreateUserCommand cmd = new CreateUserCommand(username, "sdfj*34j", List.of("api:delete", "api:update"));
            userService.create(cmd);

            ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).save(userArgumentCaptor.capture());
            User captorValue = userArgumentCaptor.getValue();
            assertThat(captorValue.getUsername()).isEqualTo(username);
            assertThat(captorValue.getPassword()).isNotNull();
            assertThat(captorValue.getAuthorities()).hasSize(2);
            assertThat(captorValue.getAuthorities()).extracting("id")
                    .isNotNull();
            assertThat(captorValue.getAuthorities()).extracting("name")
                    .containsExactlyInAnyOrder("api:delete", "api:update");
        }

        @Test
        void shouldReturnCreatedUser() {
            when(authorityService.fetchByName("api:delete"))
                    .thenReturn(anAuthority().withName("api:delete").build());
            when(authorityService.fetchByName("api:update"))
                    .thenReturn(anAuthority().withName("api:update").build());

            String username = "User 34";
            UserBuilder userBuilder = aUser().withUsername(username).withPassword("sdfj*34j")
                    .with(anAuthority().withName("api:delete"))
                    .with(anAuthority().withName("api:update"));
            when(userRepository.save(any(User.class)))
                    .thenReturn(userBuilder.build());

            CreateUserCommand cmd = new CreateUserCommand(username, "sdfj*34j", List.of("api:delete", "api:update"));
            CreateUserResponse created = userService.create(cmd);

            assertThat(created).isNotNull();
            assertThat(created.id()).isNotNull();
            assertThat(created.username()).isEqualTo(username);
            assertThat(created.authorities()).hasSize(2);
            assertThat(created.authorities()).extracting("id")
                    .hasSize(2);
            assertThat(created.authorities()).extracting("name")
                    .containsExactlyInAnyOrder("api:delete", "api:update");
        }
    }
}