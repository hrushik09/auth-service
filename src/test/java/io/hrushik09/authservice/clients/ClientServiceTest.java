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

import java.util.Optional;

import static io.hrushik09.authservice.clients.ClientBuilder.aClient;
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
        clientService = new ClientService(clientRepository);
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
            String scope = "OPENID";
            String redirectUri = "http://localhost:8080/authorized";
            String authorizationGrantType = "AUTHORIZATION_CODE";
            ClientBuilder clientBuilder = aClient().withPid(pid)
                    .withClientId(clientId)
                    .withClientSecret(clientSecret)
                    .withClientAuthenticationMethod(clientAuthenticationMethod)
                    .withScope(scope)
                    .withRedirectUri(redirectUri)
                    .withAuthorizationGrantType(authorizationGrantType);
            when(clientRepository.save(any(Client.class)))
                    .thenReturn(clientBuilder.build());

            CreateClientCommand cmd = aCommand().withPid(pid)
                    .withClientId(clientId)
                    .withClientSecret(clientSecret)
                    .withClientAuthenticationMethod(clientAuthenticationMethod)
                    .withScope(scope)
                    .withRedirectUri(redirectUri)
                    .withAuthorizationGrantType(authorizationGrantType).build();
            clientService.create(cmd);

            ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
            verify(clientRepository).save(clientArgumentCaptor.capture());
            Client captorValue = clientArgumentCaptor.getValue();
            assertThat(captorValue.getPid()).isEqualTo(pid);
            assertThat(captorValue.getClientId()).isEqualTo(clientId);
            assertThat(captorValue.getClientSecret()).isEqualTo(clientSecret);
            assertThat(captorValue.getClientAuthenticationMethod()).isEqualTo(clientAuthenticationMethod);
            assertThat(captorValue.getScope()).isEqualTo(scope);
            assertThat(captorValue.getRedirectUri()).isEqualTo(redirectUri);
            assertThat(captorValue.getAuthorizationGrantType()).isEqualTo(authorizationGrantType);
        }

        @Test
        void shouldReturnCreatedClient() {
            String pid = "rc";
            String clientId = "client";
            String clientSecret = "secret";
            ClientAuthenticationMethod clientAuthenticationMethod = ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
            String scope = "OPENID";
            String redirectUri = "http://localhost:8080/authorized";
            String authorizationGrantType = "AUTHORIZATION_CODE";
            ClientBuilder clientBuilder = aClient().withPid(pid)
                    .withClientId(clientId)
                    .withClientSecret(clientSecret)
                    .withClientAuthenticationMethod(clientAuthenticationMethod)
                    .withScope(scope)
                    .withRedirectUri(redirectUri)
                    .withAuthorizationGrantType(authorizationGrantType);
            when(clientRepository.save(any(Client.class)))
                    .thenReturn(clientBuilder.build());

            CreateClientCommand cmd = aCommand().withPid(pid)
                    .withClientId(clientId)
                    .withClientSecret(clientSecret)
                    .withClientAuthenticationMethod(clientAuthenticationMethod)
                    .withScope(scope)
                    .withRedirectUri(redirectUri)
                    .withAuthorizationGrantType(authorizationGrantType).build();
            CreateClientResponse created = clientService.create(cmd);

            assertThat(created.id()).isNotNull();
            assertThat(created.pid()).isEqualTo(pid);
            assertThat(created.clientId()).isEqualTo(clientId);
            assertThat(created.clientAuthenticationMethod()).isEqualTo(clientAuthenticationMethod);
            assertThat(created.scope()).isEqualTo(scope);
            assertThat(created.redirectUri()).isEqualTo(redirectUri);
            assertThat(created.authorizationGrantType()).isEqualTo(authorizationGrantType);
        }
    }
}