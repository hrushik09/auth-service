package io.hrushik09.authservice.authorities;

import io.hrushik09.authservice.authorities.dto.AuthorityDTO;
import io.hrushik09.authservice.authorities.dto.CreateAuthorityCommand;
import io.hrushik09.authservice.authorities.dto.CreateAuthorityResponse;
import io.hrushik09.authservice.authorities.exceptions.AuthorityAlreadyExists;
import io.hrushik09.authservice.authorities.exceptions.AuthorityDoesNotExist;
import io.hrushik09.authservice.config.SecurityConfig;
import io.hrushik09.authservice.setup.AccessControlEvaluatorTestConfig;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorityController.class)
@Import({SecurityConfig.class, AccessControlEvaluatorTestConfig.class})
public class AuthorityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthorityService authorityService;

    @Nested
    class Create {
        @Nested
        class AuthFailure {
            @Test
            void shouldReturn401WhenNotAuthenticated() throws Exception {
                mockMvc.perform(post("/api/authorities")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                        "name": "anyRandomName"
                                        }
                                        """))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @WithMockUser(username = "random-user")
            void shouldReturn403WhenUsernameIsNotDefaultAdmin() throws Exception {
                mockMvc.perform(post("/api/authorities")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                        "name": "anyRandomName"
                                        }
                                        """))
                        .andExpect(status().isForbidden());
            }

            @Test
            @WithMockUser(username = "default-admin", authorities = "randomAuthority")
            void shouldReturn403WhenUsernameIsDefaultAdminButDoesNotHaveDefaultAdminAuthority() throws Exception {
                mockMvc.perform(post("/api/authorities")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                        "name": "anyRandomName"
                                        }
                                        """))
                        .andExpect(status().isForbidden());
            }
        }

        @Nested
        @WithMockUser(username = "default-admin", authorities = "defaultAdmin")
        class AuthSuccess {
            @Test
            void shouldThrowWhenAuthorityWithNameAlreadyExists() throws Exception {
                when(authorityService.create(any(CreateAuthorityCommand.class))).thenThrow(new AuthorityAlreadyExists("duplicateAuthority"));

                mockMvc.perform(post("/api/authorities")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                        "name": "duplicateAuthority"
                                        }
                                        """))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.error", equalTo("Authority with name duplicateAuthority already exists")));
            }

            @Test
            void shouldCreateAuthority() throws Exception {
                when(authorityService.create(new CreateAuthorityCommand("api:read")))
                        .thenReturn(new CreateAuthorityResponse(1, "api:read"));

                mockMvc.perform(post("/api/authorities")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                        "name": "api:read"
                                        }
                                        """)
                        )
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id", notNullValue()))
                        .andExpect(jsonPath("$.name", equalTo("api:read")));
            }
        }
    }

    @Nested
    class FetchById {
        @Nested
        class AuthFailure {
            @Test
            void shouldReturn401WhenNotAuthenticated() throws Exception {
                mockMvc.perform(get("/api/authorities/{id}", 54))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @WithMockUser(username = "random-user")
            void shouldReturn403WhenUsernameIsNotDefaultAdmin() throws Exception {
                mockMvc.perform(get("/api/authorities/{id}", 65))
                        .andExpect(status().isForbidden());
            }

            @Test
            @WithMockUser(username = "default-admin", authorities = "randomAuthority")
            void shouldReturn403WhenUsernameIsDefaultAdminButDoesNotHaveDefaultAdminAuthority() throws Exception {
                mockMvc.perform(get("/api/authorities/{id}", 98))
                        .andExpect(status().isForbidden());
            }
        }

        @Nested
        @WithMockUser(username = "default-admin", authorities = "defaultAdmin")
        class AuthSuccess {
            @Test
            void shouldThrowWhenFetchingNonExistingAuthority() throws Exception {
                Integer nonExistingId = 9;
                when(authorityService.fetchById(nonExistingId))
                        .thenThrow(new AuthorityDoesNotExist(nonExistingId));

                mockMvc.perform(get("/api/authorities/{id}", nonExistingId))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.error", equalTo("Authority with id " + nonExistingId + " does not exist")));
            }

            @Test
            void shouldFetchAuthorityById() throws Exception {
                Integer id = 4;
                String name = "domain:read";
                when(authorityService.fetchById(id))
                        .thenReturn(new AuthorityDTO(id, name, Instant.now(), Instant.now()));

                mockMvc.perform(get("/api/authorities/{id}", id))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id", equalTo(id)))
                        .andExpect(jsonPath("$.name", equalTo(name)))
                        .andExpect(jsonPath("$.createdAt", notNullValue()))
                        .andExpect(jsonPath("$.updatedAt", notNullValue()));
            }
        }
    }
}
