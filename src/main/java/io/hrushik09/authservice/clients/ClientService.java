package io.hrushik09.authservice.clients;

import io.hrushik09.authservice.clients.dto.CreateClientCommand;
import io.hrushik09.authservice.clients.dto.CreateClientResponse;
import io.hrushik09.authservice.clients.exceptions.ClientDoesNotExist;
import io.hrushik09.authservice.clients.exceptions.ClientIdAlreadyExistsException;
import io.hrushik09.authservice.clients.exceptions.PidAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        clientRepository.existsByPid(cmd.pid())
                .ifPresent(client -> {
                    throw new PidAlreadyExistsException(cmd.pid());
                });
        clientRepository.existsByClientId(cmd.clientId())
                .ifPresent(client -> {
                    throw new ClientIdAlreadyExistsException(cmd.clientId());
                });
        Client client = new Client();
        client.setPid(cmd.pid());
        client.setClientId(cmd.clientId());
        client.setClientSecret(passwordEncoder.encode(cmd.clientSecret()));
        client.setAuthenticationMethod(cmd.authenticationMethod());
        List<ClientScope> clientScopes = cmd.scopes().stream()
                .map(ClientScope::new)
                .toList();
        client.setClientScopes(clientScopes);
        List<ClientRedirectUri> clientRedirectUris = cmd.redirectUris().stream()
                .map(ClientRedirectUri::new)
                .toList();
        client.setClientRedirectUris(clientRedirectUris);
        List<ClientAuthorizationGrantType> clientAuthorizationGrantTypes = cmd.authorizationGrantTypes().stream()
                .map(ClientAuthorizationGrantType::new)
                .toList();
        client.setClientAuthorizationGrantTypes(clientAuthorizationGrantTypes);
        Client saved = clientRepository.save(client);
        return CreateClientResponse.from(saved);
    }

    public Client findByPid(String pid) {
        return clientRepository.findByPid(pid)
                .orElseThrow(() -> new ClientDoesNotExist("Client with pid " + pid + " does not exist"));
    }
}
