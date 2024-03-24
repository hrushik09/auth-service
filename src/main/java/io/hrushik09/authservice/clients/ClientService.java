package io.hrushik09.authservice.clients;

import io.hrushik09.authservice.clients.dto.CreateClientCommand;
import io.hrushik09.authservice.clients.dto.CreateClientResponse;
import io.hrushik09.authservice.clients.exceptions.ClientIdAlreadyExistsException;
import io.hrushik09.authservice.clients.exceptions.PidAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CreateClientResponse create(CreateClientCommand cmd) {
        clientRepository.findByPid(cmd.pid())
                .ifPresent(client -> {
                    throw new PidAlreadyExistsException(cmd.pid());
                });
        clientRepository.findByClientId(cmd.clientId())
                .ifPresent(client -> {
                    throw new ClientIdAlreadyExistsException(cmd.clientId());
                });
        Client client = new Client();
        client.setPid(cmd.pid());
        client.setClientId(cmd.clientId());
        client.setClientSecret(passwordEncoder.encode(cmd.clientSecret()));
        client.setClientAuthenticationMethod(cmd.clientAuthenticationMethod());
        client.setScope(cmd.scope());
        client.setRedirectUri(cmd.redirectUri());
        client.setAuthorizationGrantType(cmd.authorizationGrantType());
        Client saved = clientRepository.save(client);
        return CreateClientResponse.from(saved);
    }
}
