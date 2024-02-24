package io.hrushik09.authservice.authorities;

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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
                mockMvc.perform(post("/authorities"))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @WithMockUser(username = "random-user")
            void shouldReturn403WhenUsernameIsNotDefaultAdmin() throws Exception {
                mockMvc.perform(post("/authorities")
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
                mockMvc.perform(post("/authorities")
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
                when(authorityService.create(any())).thenThrow(new AuthorityAlreadyExists("duplicateAuthority"));

                mockMvc.perform(post("/authorities")
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

                mockMvc.perform(post("/authorities")
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
}
