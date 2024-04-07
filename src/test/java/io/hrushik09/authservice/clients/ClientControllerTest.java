package io.hrushik09.authservice.clients;

import io.hrushik09.authservice.clients.dto.CreateClientCommand;
import io.hrushik09.authservice.clients.dto.CreateClientResponse;
import io.hrushik09.authservice.clients.exceptions.ClientIdAlreadyExistsException;
import io.hrushik09.authservice.clients.exceptions.PidAlreadyExistsException;
import io.hrushik09.authservice.config.security.SecurityConfig;
import io.hrushik09.authservice.setup.RegisteredClientRepositoryWebMvcTestConfig;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static io.hrushik09.authservice.clients.dto.CreateClientCommandBuilder.aCommand;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClientController.class)
@Import({SecurityConfig.class, RegisteredClientRepositoryWebMvcTestConfig.class})
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
                        "pid": "randomPid",
                        "clientId": "randomClientId",
                        "clientSecret": "randomSecret",
                        "authenticationMethod": "CLIENT_SECRET_BASIC",
                        "scopes": [
                        "OPENID"
                        ],
                        "redirectUris": [
                        "randomRedirectUri1",
                        "randomRedirectUri2"
                        ],
                        "authorizationGrantTypes": [
                        "AUTHORIZATION_CODE",
                        "REFRESH_TOKEN"
                        ]
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
            void shouldNotCreateClientWhenPidAlreadyExists() throws Exception {
                when(clientService.create(any(CreateClientCommand.class)))
                        .thenThrow(new PidAlreadyExistsException("duplicatePid"));

                mockMvc.perform(post("/api/clients")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                        {
                                        "pid": "duplicatePid",
                                        "clientId": "randomClientId",
                                        "clientSecret": "randomSecret",
                                        "authenticationMethod": "CLIENT_SECRET_BASIC",
                                        "scopes": [
                                        "OPENID"
                                        ],
                                        "redirectUris": [
                                        "randomRedirectUri1",
                                        "randomRedirectUri2"
                                        ],
                                        "authorizationGrantTypes": [
                                        "AUTHORIZATION_CODE",
                                        "REFRESH_TOKEN"
                                        ]
                                        }
                                        """))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.error", equalTo("Client with pid duplicatePid already exists")));
            }

            @Test
            void shouldNotCreateClientWhenClientIdAlreadyExists() throws Exception {
                when(clientService.create(any(CreateClientCommand.class)))
                        .thenThrow(new ClientIdAlreadyExistsException("duplicateClientId"));

                mockMvc.perform(post("/api/clients")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                        {
                                        "pid": "randomPid",
                                        "clientId": "duplicateClientId",
                                        "clientSecret": "randomSecret",
                                        "authenticationMethod": "CLIENT_SECRET_BASIC",
                                        "scopes": [
                                        "OPENID"
                                        ],
                                        "redirectUris": [
                                        "randomRedirectUri1",
                                        "randomRedirectUri2"
                                        ],
                                        "authorizationGrantTypes": [
                                        "AUTHORIZATION_CODE",
                                        "REFRESH_TOKEN"
                                        ]
                                        }
                                        """))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.error", equalTo("Client with clientId duplicateClientId already exists")));
            }

            @Test
            void shouldCreateClient() throws Exception {
                List<String> scopes = List.of("OPENID", "api:read", "api:create");
                List<String> redirectUris = List.of("http://localhost:8080/authorized", "http://localhost:8080/api/authorized");
                List<AuthorizationGrantType> authorizationGrantTypes = List.of(AuthorizationGrantType.AUTHORIZATION_CODE, AuthorizationGrantType.REFRESH_TOKEN);
                CreateClientCommand cmd = aCommand().withPid("rc")
                        .withClientId("client1")
                        .withClientSecret("secret")
                        .withAuthenticationMethod(AuthenticationMethod.CLIENT_SECRET_BASIC)
                        .withScopes(scopes)
                        .withRedirectUris(redirectUris)
                        .withAuthorizationGrantTypes(authorizationGrantTypes)
                        .build();
                CreateClientResponse response = new CreateClientResponse(34, "rc", "client1", AuthenticationMethod.CLIENT_SECRET_BASIC, scopes, redirectUris, authorizationGrantTypes);
                when(clientService.create(cmd)).thenReturn(response);

                mockMvc.perform(post("/api/clients")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                        {
                                        "pid": "rc",
                                        "clientId": "client1",
                                        "clientSecret": "secret",
                                        "authenticationMethod": "CLIENT_SECRET_BASIC",
                                        "scopes": [
                                        "OPENID",
                                        "api:read",
                                        "api:create"
                                        ],
                                        "redirectUris": [
                                        "http://localhost:8080/authorized",
                                        "http://localhost:8080/api/authorized"
                                        ],
                                        "authorizationGrantTypes": [
                                        "AUTHORIZATION_CODE",
                                        "REFRESH_TOKEN"
                                        ]
                                        }
                                        """))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id", notNullValue()))
                        .andExpect(jsonPath("$.pid", equalTo("rc")))
                        .andExpect(jsonPath("$.clientId", equalTo("client1")))
                        .andExpect(jsonPath("$.authenticationMethod", equalTo("CLIENT_SECRET_BASIC")))
                        .andExpect(jsonPath("$.scopes", hasSize(3)))
                        .andExpect(jsonPath("$.scopes", containsInAnyOrder("OPENID", "api:read", "api:create")))
                        .andExpect(jsonPath("$.redirectUris", hasSize(2)))
                        .andExpect(jsonPath("$.redirectUris", containsInAnyOrder("http://localhost:8080/authorized", "http://localhost:8080/api/authorized")))
                        .andExpect(jsonPath("$.authorizationGrantTypes", hasSize(2)))
                        .andExpect(jsonPath("$.authorizationGrantTypes", containsInAnyOrder("AUTHORIZATION_CODE", "REFRESH_TOKEN")));
            }
        }
    }
}
