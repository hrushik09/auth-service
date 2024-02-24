package io.hrushik09.authservice.authorities;

import io.hrushik09.authservice.config.SecurityConfig;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorityController.class)
@Import(SecurityConfig.class)
public class AuthorityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Nested
    class CreateAuthority {
        @Test
        void shouldReturn401WhenNotAuthenticated() throws Exception {
            mockMvc.perform(post("/authorities"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void shouldReturn403WhenUsernameIsNotDefaultAdmin() throws Exception {
            mockMvc.perform(post("/authorities")
                            .with(user("random-user")))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "default-admin", authorities = "randomAuthority")
        void shouldReturn403WhenUsernameIsDefaultAdminButDoesNotHaveDefaultAdminAuthority() throws Exception {
            mockMvc.perform(post("/authorities"))
                    .andExpect(status().isForbidden());
        }
    }
}
