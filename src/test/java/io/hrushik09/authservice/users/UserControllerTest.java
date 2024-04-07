package io.hrushik09.authservice.users;

import io.hrushik09.authservice.authorities.exceptions.AuthorityDoesNotExist;
import io.hrushik09.authservice.config.security.SecurityConfig;
import io.hrushik09.authservice.setup.AccessControlEvaluatorTestConfig;
import io.hrushik09.authservice.setup.LoggingServiceTestConfig;
import io.hrushik09.authservice.setup.RegisteredClientRepositoryWebMvcTestConfig;
import io.hrushik09.authservice.users.dto.CreateUserCommand;
import io.hrushik09.authservice.users.dto.CreateUserResponse;
import io.hrushik09.authservice.users.dto.UserAuthorityDTO;
import io.hrushik09.authservice.users.exceptions.UsernameAlreadyExistsException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({SecurityConfig.class, AccessControlEvaluatorTestConfig.class, RegisteredClientRepositoryWebMvcTestConfig.class, LoggingServiceTestConfig.class})
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Nested
    class Create {
        @Nested
        class AuthFailure {
            private static String getRandomContent() {
                return """
                        {
                        "username": "randomUsername",
                        "password": "randomPassword",
                        "authorities": [
                            "randomAuthority"
                        ]
                        }
                        """;
            }

            @Test
            void shouldReturn401WhenNotAuthenticated() throws Exception {
                mockMvc.perform(post("/api/users")
                                .contentType(APPLICATION_JSON)
                                .content(getRandomContent()))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @WithMockUser(username = "random-user")
            void shouldReturn403WhenUsernameIsNotDefaultAdmin() throws Exception {
                mockMvc.perform(post("/api/users")
                                .contentType(APPLICATION_JSON)
                                .content(getRandomContent()))
                        .andExpect(status().isForbidden());
            }

            @Test
            @WithMockUser(username = "default-admin", authorities = "randomAuthority")
            void shouldReturn403WhenUsernameIsDefaultAdminButDoesNotHaveDefaultAdminAuthority() throws Exception {
                mockMvc.perform(post("/api/users")
                                .contentType(APPLICATION_JSON)
                                .content(getRandomContent()))
                        .andExpect(status().isForbidden());
            }
        }

        @Nested
        @WithMockUser(username = "default-admin", authorities = "defaultAdmin")
        class AuthSuccess {
            @Test
            void shouldNotCreateUserIfUsernameAlreadyExists() throws Exception {
                when(userService.create(any(CreateUserCommand.class)))
                        .thenThrow(new UsernameAlreadyExistsException("duplicateUsername"));

                mockMvc.perform(post("/api/users")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                        {
                                        "username": "duplicateUsername",
                                        "password": "doesnt-matter",
                                        "authorities": [
                                            "randomAuthority"
                                        ]
                                        }
                                        """))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.error", equalTo("User with username duplicateUsername already exists")));
            }

            @Test
            void shouldNotCreateUserIfAuthorityDoesNotExist() throws Exception {
                when(userService.create(any(CreateUserCommand.class)))
                        .thenThrow(new AuthorityDoesNotExist("doesNotExistAuthority"));

                mockMvc.perform(post("/api/users")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                        {
                                        "username": "doesnt matter",
                                        "password": "doesnt matter",
                                        "authorities": [
                                            "newAuthority",
                                            "doesNotExistAuthority"
                                        ]
                                        }
                                        """))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.error", equalTo("Authority with name doesNotExistAuthority does not exist")));
            }

            @Test
            void shouldCreateUser() throws Exception {
                CreateUserCommand cmd = new CreateUserCommand("User 34", "jhsu^223", List.of("api:delete", "api:update"));
                CreateUserResponse response = new CreateUserResponse(43, "User 34", List.of(
                        new UserAuthorityDTO(23, "api:delete"),
                        new UserAuthorityDTO(34, "api:update")));
                when(userService.create(cmd)).thenReturn(response);

                mockMvc.perform(post("/api/users")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                        {
                                        "username": "User 34",
                                        "password": "jhsu^223",
                                        "authorities": [
                                            "api:delete",
                                            "api:update"
                                        ]
                                        }
                                        """))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id", notNullValue()))
                        .andExpect(jsonPath("$.username", equalTo("User 34")))
                        .andExpect(jsonPath("$.authorities", hasSize(2)))
                        .andExpect(jsonPath("$.authorities[*].id", hasSize(2)))
                        .andExpect(jsonPath("$.authorities[*].name", containsInAnyOrder("api:delete", "api:update")));
            }
        }
    }
}
