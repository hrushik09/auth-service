package io.hrushik09.authservice.clients;

import io.hrushik09.authservice.clients.dto.CreateClientCommand;
import io.hrushik09.authservice.clients.dto.CreateClientResponse;
import io.hrushik09.authservice.clients.exceptions.ClientIdAlreadyExistsException;
import io.hrushik09.authservice.clients.exceptions.IdAlreadyExistsException;
import io.hrushik09.authservice.config.SecurityConfig;
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

@WebMvcTest(controllers = ClientController.class)
@Import(SecurityConfig.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClientService clientService;

    @Nested
    class Create {
        @Nested
        class AuthFailure {
            private static String getRandomContent() {
                return """
                        {
                        "id": "randomId",
                        "clientId": "randomClientId",
                        "clientSecret": "randomSecret",
                        "clientAuthenticationMethod": "CLIENT_SECRET_BASIC",
                        "scope": "randomScope",
                        "redirectUri": "randomRedirectUri",
                        "authorizationGrantType": "randomAuthorizationGrantType"
                        }
                        """;
            }

            @Test
            void shouldReturn401WhenNotAuthenticated() throws Exception {
                mockMvc.perform(post("/api/clients")
                                .contentType(APPLICATION_JSON)
                                .content(getRandomContent()))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @WithMockUser(authorities = "random:authority")
            void shouldReturn403WhenDoesNotHaveClientsCreateAuthority() throws Exception {
                mockMvc.perform(post("/api/clients")
                                .contentType(APPLICATION_JSON)
                                .content(getRandomContent()))
                        .andExpect(status().isForbidden());
            }
        }

        @Nested
        @WithMockUser(authorities = "clients:create")
        class AuthSuccess {
            @Test
            void shouldNotCreateClientWhenIdAlreadyExists() throws Exception {
                when(clientService.create(any(CreateClientCommand.class)))
                        .thenThrow(new IdAlreadyExistsException("duplicateId"));

                mockMvc.perform(post("/api/clients")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                        {
                                        "id": "duplicateId",
                                        "clientId": "randomClientId",
                                        "clientSecret": "randomSecret",
                                        "clientAuthenticationMethod": "CLIENT_SECRET_BASIC",
                                        "scope": "randomScope",
                                        "redirectUri": "randomRedirectUri",
                                        "authorizationGrantType": "randomAuthorizationGrantType"
                                        }
                                        """))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.error", equalTo("Client with id duplicateId already exists")));
            }

            @Test
            void shouldNotCreateClientWhenClientIdAlreadyExists() throws Exception {
                when(clientService.create(any(CreateClientCommand.class)))
                        .thenThrow(new ClientIdAlreadyExistsException("duplicateClientId"));

                mockMvc.perform(post("/api/clients")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                        {
                                        "id": "randomId",
                                        "clientId": "duplicateClientId",
                                        "clientSecret": "randomSecret",
                                        "clientAuthenticationMethod": "CLIENT_SECRET_BASIC",
                                        "scope": "randomScope",
                                        "redirectUri": "randomRedirectUri",
                                        "authorizationGrantType": "randomAuthorizationGrantType"
                                        }
                                        """))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.error", equalTo("Client with clientId duplicateClientId already exists")));
            }

            @Test
            void shouldCreateClient() throws Exception {
                CreateClientCommand cmd = new CreateClientCommand("rc", "client1", "secret", ClientAuthenticationMethod.CLIENT_SECRET_BASIC, "OPENID", "http://localhost:8080/authorized", "AUTHORIZATION_CODE");
                CreateClientResponse response = new CreateClientResponse("rc", "client1", ClientAuthenticationMethod.CLIENT_SECRET_BASIC, "OPENID", "http://localhost:8080/authorized", "AUTHORIZATION_CODE");
                when(clientService.create(cmd)).thenReturn(response);

                mockMvc.perform(post("/api/clients")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                        {
                                        "id": "rc",
                                        "clientId": "client1",
                                        "clientSecret": "secret",
                                        "clientAuthenticationMethod": "CLIENT_SECRET_BASIC",
                                        "scope": "OPENID",
                                        "redirectUri": "http://localhost:8080/authorized",
                                        "authorizationGrantType": "AUTHORIZATION_CODE"
                                        }
                                        """))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id", equalTo("rc")))
                        .andExpect(jsonPath("$.clientId", equalTo("client1")))
                        .andExpect(jsonPath("$.clientAuthenticationMethod", equalTo("CLIENT_SECRET_BASIC")))
                        .andExpect(jsonPath("$.scope", equalTo("OPENID")))
                        .andExpect(jsonPath("$.redirectUri", equalTo("http://localhost:8080/authorized")))
                        .andExpect(jsonPath("$.authorizationGrantType", equalTo("AUTHORIZATION_CODE")));
            }
        }
    }
}
