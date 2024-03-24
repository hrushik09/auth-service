package io.hrushik09.authservice.clients;

import java.util.Optional;

public class ClientRepository {
    public Optional<Client> findByPid(String pid) {
        return Optional.empty();
    }

    public Optional<Client> findByClientId(String clientId) {
        return Optional.empty();
    }

    public Client save(Client client) {
        return null;
    }
}
