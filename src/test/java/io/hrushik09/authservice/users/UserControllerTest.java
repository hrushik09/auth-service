package io.hrushik09.authservice.users;

import io.hrushik09.authservice.authorities.exceptions.AuthorityDoesNotExist;
import io.hrushik09.authservice.config.SecurityConfig;
import io.hrushik09.authservice.setup.AccessControlEvaluatorTestConfig;
import io.hrushik09.authservice.users.dto.CreateUserCommand;
import io.hrushik09.authservice.users.exceptions.UsernameAlreadyExistsException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({SecurityConfig.class, AccessControlEvaluatorTestConfig.class})
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Nested
    class Create {
        @Nested
        class AuthFailure {
            @Test
            void shouldReturn401WhenNotAuthenticated() throws Exception {
                mockMvc.perform(post("/api/users")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                        {
                                        "username": "randomUsername",
                                        "password": "randomPassword",
                                        "authorities": [
                                            "randomAuthority"
                                        ]
                                        }
                                        """))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @WithMockUser(username = "random-user")
            void shouldReturn403WhenUsernameIsNotDefaultAdmin() throws Exception {
                mockMvc.perform(post("/api/users")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                        {
                                        "username": "randomUsername",
                                        "password": "randomPassword",
                                        "authorities": [
                                            "randomAuthority"
                                        ]
                                        }
                                        """))
                        .andExpect(status().isForbidden());
            }

            @Test
            @WithMockUser(username = "default-admin", authorities = "randomAuthority")
            void shouldReturn403WhenUsernameIsDefaultAdminButDoesNotHaveDefaultAdminAuthority() throws Exception {
                mockMvc.perform(post("/api/users")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                        {
                                        "username": "randomUsername",
                                        "password": "randomPassword",
                                        "authorities": [
                                            "randomAuthority"
                                        ]
                                        }
                                        """))
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
        }
    }
}
