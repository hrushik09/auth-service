package io.hrushik09.authservice.clients;

import io.hrushik09.authservice.clients.dto.CreateClientCommand;
import io.hrushik09.authservice.clients.exceptions.ClientIdAlreadyExistsException;
import io.hrushik09.authservice.clients.exceptions.PidAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.hrushik09.authservice.clients.ClientBuilder.aClient;
import static io.hrushik09.authservice.clients.dto.CreateClientCommandBuilder.aCommand;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    }
}