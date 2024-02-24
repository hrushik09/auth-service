package io.hrushik09.authservice.authorities;

import io.hrushik09.authservice.config.SecurityConfig;
import io.hrushik09.authservice.setup.AccessControlEvaluatorTestConfig;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorityController.class)
@Import({SecurityConfig.class, AccessControlEvaluatorTestConfig.class})
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
        @WithMockUser(username = "random-user")
        void shouldReturn403WhenUsernameIsNotDefaultAdmin() throws Exception {
            mockMvc.perform(post("/authorities"))
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
