package io.hrushik09.authservice.clients;

import io.hrushik09.authservice.setup.RepositoryTest;
import io.hrushik09.authservice.setup.RepositoryTestDataPersister;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class ClientRepositoryTest {
    private final RepositoryTestDataPersister having = new RepositoryTestDataPersister();
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ClientRepository clientRepository;

    @Test
    void validateFindByPid() {
        Client client1 = having.persistedClient(entityManager, "pid1", "doesnt-matter-1");
        having.persistedClient(entityManager, "pid2", "doesnt-matter-2");
        entityManager.flush();
        entityManager.clear();

        Optional<Client> optionalClient = clientRepository.existsByPid(client1.getPid());

        assertThat(optionalClient).isPresent();
        Client fetched = optionalClient.get();
        assertThat(fetched.getPid()).isEqualTo(client1.getPid());
    }

    @Test
    void validateFindByClientId() {
        Client client1 = having.persistedClient(entityManager, "doesnt-matter-1", "clientId1");
        having.persistedClient(entityManager, "doesnt-matter-2", "clientId2");
        entityManager.flush();
        entityManager.clear();

        Optional<Client> optionalClient = clientRepository.existsByClientId(client1.getClientId());

        assertThat(optionalClient).isPresent();
        Client fetched = optionalClient.get();
        assertThat(fetched.getClientId()).isEqualTo(client1.getClientId());
    }
}