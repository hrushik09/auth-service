package io.hrushik09.authservice.clients;

import io.hrushik09.authservice.clients.dto.CreateClientCommand;
import io.hrushik09.authservice.clients.dto.CreateClientResponse;
import io.hrushik09.authservice.clients.exceptions.ClientIdAlreadyExistsException;
import io.hrushik09.authservice.clients.exceptions.PidAlreadyExistsException;

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public CreateClientResponse create(CreateClientCommand cmd) {
        clientRepository.findByPid(cmd.pid())
                .ifPresent(client -> {
                    throw new PidAlreadyExistsException(cmd.pid());
                });
        clientRepository.findByClientId(cmd.clientId())
                .ifPresent(client -> {
                    throw new ClientIdAlreadyExistsException(cmd.clientId());
                });
        return null;
    }
}
