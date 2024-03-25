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
            ClientAuthenticationMethod clientAuthenticationMethod = ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
            String redirectUri = "http://localhost:8080/authorized";
            String authorizationGrantType = "AUTHORIZATION_CODE";
            ClientBuilder clientBuilder = aClient().withPid(pid)
                    .withClientId(clientId)
                    .withClientSecret(clientSecret)
                    .withClientAuthenticationMethod(clientAuthenticationMethod)
                    .with(aClientScope().withId(2).withValue("OPENID"))
                    .with(aClientScope().withId(3).withValue("api:read"))
                    .with(aClientScope().withId(4).withValue("api:create"))
                    .withRedirectUri(redirectUri)
                    .withAuthorizationGrantType(authorizationGrantType);
            when(clientRepository.save(any(Client.class)))
                    .thenReturn(clientBuilder.build());

            CreateClientCommand cmd = aCommand().withPid(pid)
                    .withClientId(clientId)
                    .withClientSecret(clientSecret)
                    .withClientAuthenticationMethod(clientAuthenticationMethod)
                    .withScopes(List.of("OPENID", "api:read", "api:create"))
                    .withRedirectUri(redirectUri)
                    .withAuthorizationGrantType(authorizationGrantType).build();
            clientService.create(cmd);

            ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
            verify(clientRepository).save(clientArgumentCaptor.capture());
            Client captorValue = clientArgumentCaptor.getValue();
            assertThat(captorValue.getPid()).isEqualTo(pid);
            assertThat(captorValue.getClientId()).isEqualTo(clientId);
            assertThat(captorValue.getClientSecret()).isNotNull();
            assertThat(captorValue.getClientAuthenticationMethod()).isEqualTo(clientAuthenticationMethod);
            assertThat(captorValue.getClientScopes()).hasSize(3);
            assertThat(captorValue.getClientScopes()).extracting("value")
                    .containsExactlyInAnyOrder("OPENID", "api:read", "api:create");
            assertThat(captorValue.getRedirectUri()).isEqualTo(redirectUri);
            assertThat(captorValue.getAuthorizationGrantType()).isEqualTo(authorizationGrantType);
        }

        @Test
        void shouldReturnCreatedClient() {
            String pid = "rc";
            String clientId = "client";
            String clientSecret = "secret";
            ClientAuthenticationMethod clientAuthenticationMethod = ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
            String redirectUri = "http://localhost:8080/authorized";
            String authorizationGrantType = "AUTHORIZATION_CODE";
            ClientBuilder clientBuilder = aClient().withPid(pid)
                    .withClientId(clientId)
                    .withClientSecret(clientSecret)
                    .withClientAuthenticationMethod(clientAuthenticationMethod)
                    .with(aClientScope().withId(2).withValue("OPENID"))
                    .with(aClientScope().withId(3).withValue("api:read"))
                    .with(aClientScope().withId(4).withValue("api:create"))
                    .withRedirectUri(redirectUri)
                    .withAuthorizationGrantType(authorizationGrantType);
            when(clientRepository.save(any(Client.class)))
                    .thenReturn(clientBuilder.build());

            CreateClientCommand cmd = aCommand().withPid(pid)
                    .withClientId(clientId)
                    .withClientSecret(clientSecret)
                    .withClientAuthenticationMethod(clientAuthenticationMethod)
                    .withScopes(List.of("OPENID", "api:read", "api:create"))
                    .withRedirectUri(redirectUri)
                    .withAuthorizationGrantType(authorizationGrantType).build();
            CreateClientResponse created = clientService.create(cmd);

            assertThat(created.id()).isNotNull();
            assertThat(created.pid()).isEqualTo(pid);
            assertThat(created.clientId()).isEqualTo(clientId);
            assertThat(created.clientAuthenticationMethod()).isEqualTo(clientAuthenticationMethod);
            assertThat(created.scopes()).hasSize(3);
            assertThat(created.scopes()).containsExactlyInAnyOrder("OPENID", "api:read", "api:create");
            assertThat(created.redirectUri()).isEqualTo(redirectUri);
            assertThat(created.authorizationGrantType()).isEqualTo(authorizationGrantType);
        }
    }
}