package io.hrushik09.authservice.clients;

import io.hrushik09.authservice.config.SecurityConfig;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClientController.class)
@Import(SecurityConfig.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Nested
    class Create {
        @Nested
        class AuthFailure {
            @Test
            void shouldReturn401WhenNotAuthenticated() throws Exception {
                mockMvc.perform(post("/api/clients")
                                .contentType(APPLICATION_JSON))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @WithMockUser(authorities = "random:authority")
            void shouldReturn403WhenDoesNotHaveClientsCreateAuthority() throws Exception {
                mockMvc.perform(post("/api/clients")
                                .contentType(APPLICATION_JSON))
                        .andExpect(status().isForbidden());
            }
        }
    }
}
