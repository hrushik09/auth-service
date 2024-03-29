package io.hrushik09.authservice.clients;

import io.hrushik09.authservice.clients.dto.CreateClientCommand;
import io.hrushik09.authservice.clients.dto.CreateClientResponse;
import io.hrushik09.authservice.clients.exceptions.ClientIdAlreadyExistsException;
import io.hrushik09.authservice.clients.exceptions.PidAlreadyExistsException;
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

import static io.hrushik09.authservice.clients.ClientBuilder.aClient;
import static io.hrushik09.authservice.clients.ClientRedirectUriBuilder.aClientRedirectUri;
import static io.hrushik09.authservice.clients.ClientScopeBuilder.aClientScope;
import static io.hrushik09.authservice.clients.dto.CreateClientCommandBuilder.aCommand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    private ClientService clientService;
    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        clientService = new ClientService(clientRepository, passwordEncoder);
    }

    @Nested
    class Create {
        @Test
        void shouldThrowWhenPidAlreadyExists() {
            String duplicatePid = "duplicatePid";
            when(clientRepository.findByPid(duplicatePid))
                    .thenReturn(Optional.of(aClient().withPid(duplicatePid).build()));

            CreateClientCommand cmd = aCommand().withPid(duplicatePid).build();
            assertThatThrownBy(() -> clientService.create(cmd))
                    .isInstanceOf(PidAlreadyExistsException.class)
                    .hasMessage("Client with pid " + duplicatePid + " already exists");
        }

        @Test
        void shouldThrowWhenClientIdAlreadyExists() {
            String duplicateClientId = "duplicateClientId";
            when(clientRepository.findByClientId(duplicateClientId))
                    .thenReturn(Optional.of(aClient().withClientId(duplicateClientId).build()));

            CreateClientCommand cmd = aCommand().withClientId(duplicateClientId).build();
            assertThatThrownBy(() -> clientService.create(cmd))
                    .isInstanceOf(ClientIdAlreadyExistsException.class)
                    .hasMessage("Client with clientId " + duplicateClientId + " already exists");
        }

        @Test
        void shouldSaveUsingRepositoryWhenCreatingClient() {
            String pid = "rc";
            String clientId = "client";
            String clientSecret = "secret";
            AuthenticationMethod authenticationMethod = AuthenticationMethod.CLIENT_SECRET_BASIC;
            String authorizationGrantType = "AUTHORIZATION_CODE";
            ClientBuilder clientBuilder = aClient().withPid(pid)
                    .withClientId(clientId)
                    .withClientSecret(clientSecret)
                    .withAuthenticationMethod(authenticationMethod)
                    .with(aClientScope().withId(2).withValue("OPENID"))
                    .with(aClientScope().withId(3).withValue("api:read"))
                    .with(aClientScope().withId(4).withValue("api:create"))
                    .with(aClientRedirectUri().withId(5).withValue("http://localhost:8080/authorized"))
                    .with(aClientRedirectUri().withId(8).withValue("http://localhost:8080/api/authorized"))
                    .withAuthorizationGrantType(authorizationGrantType);
            when(clientRepository.save(any(Client.class)))
                    .thenReturn(clientBuilder.build());

            CreateClientCommand cmd = aCommand().withPid(pid)
                    .withClientId(clientId)
                    .withClientSecret(clientSecret)
                    .withAuthenticationMethod(authenticationMethod)
                    .withScopes(List.of("OPENID", "api:read", "api:create"))
                    .withRedirectUris(List.of("http://localhost:8080/authorized", "http://localhost:8080/api/authorized"))
                    .withAuthorizationGrantTypes(List.of(AuthorizationGrantType.valueOf(authorizationGrantType))).build();
            clientService.create(cmd);

            ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
            verify(clientRepository).save(clientArgumentCaptor.capture());
            Client captorValue = clientArgumentCaptor.getValue();
            assertThat(captorValue.getPid()).isEqualTo(pid);
            assertThat(captorValue.getClientId()).isEqualTo(clientId);
            assertThat(captorValue.getClientSecret()).isNotNull();
            assertThat(captorValue.getAuthenticationMethod()).isEqualTo(authenticationMethod);
            assertThat(captorValue.getClientScopes()).hasSize(3);
            assertThat(captorValue.getClientScopes()).extracting("value")
                    .containsExactlyInAnyOrder("OPENID", "api:read", "api:create");
            assertThat(captorValue.getClientRedirectUris()).hasSize(2);
            assertThat(captorValue.getClientRedirectUris()).extracting("value")
                    .containsExactlyInAnyOrder("http://localhost:8080/authorized", "http://localhost:8080/api/authorized");
            assertThat(captorValue.getAuthorizationGrantType()).isEqualTo(authorizationGrantType);
        }

        @Test
        void shouldReturnCreatedClient() {
            String pid = "rc";
            String clientId = "client";
            String clientSecret = "secret";
            AuthenticationMethod authenticationMethod = AuthenticationMethod.CLIENT_SECRET_BASIC;
            String authorizationGrantType = "AUTHORIZATION_CODE";
            ClientBuilder clientBuilder = aClient().withPid(pid)
                    .withClientId(clientId)
                    .withClientSecret(clientSecret)
                    .withAuthenticationMethod(authenticationMethod)
                    .with(aClientScope().withId(2).withValue("OPENID"))
                    .with(aClientScope().withId(3).withValue("api:read"))
                    .with(aClientScope().withId(4).withValue("api:create"))
                    .with(aClientRedirectUri().withId(5).withValue("http://localhost:8080/authorized"))
                    .with(aClientRedirectUri().withId(8).withValue("http://localhost:8080/api/authorized"))
                    .withAuthorizationGrantType(authorizationGrantType);
            when(clientRepository.save(any(Client.class)))
                    .thenReturn(clientBuilder.build());

            CreateClientCommand cmd = aCommand().withPid(pid)
                    .withClientId(clientId)
                    .withClientSecret(clientSecret)
                    .withAuthenticationMethod(authenticationMethod)
                    .withScopes(List.of("OPENID", "api:read", "api:create"))
                    .withRedirectUris(List.of("http://localhost:8080/authorized", "http://localhost:8080/api/authorized"))
                    .withAuthorizationGrantTypes(List.of(AuthorizationGrantType.valueOf(authorizationGrantType))).build();
            CreateClientResponse created = clientService.create(cmd);

            assertThat(created.id()).isNotNull();
            assertThat(created.pid()).isEqualTo(pid);
            assertThat(created.clientId()).isEqualTo(clientId);
            assertThat(created.authenticationMethod()).isEqualTo(authenticationMethod);
            assertThat(created.scopes()).hasSize(3);
            assertThat(created.scopes()).containsExactlyInAnyOrder("OPENID", "api:read", "api:create");
            assertThat(created.redirectUris()).hasSize(2);
            assertThat(created.redirectUris())
                    .containsExactlyInAnyOrder("http://localhost:8080/authorized", "http://localhost:8080/api/authorized");
            assertThat(created.authorizationGrantTypes().getFirst().name()).isEqualTo(authorizationGrantType);
        }
    }
}