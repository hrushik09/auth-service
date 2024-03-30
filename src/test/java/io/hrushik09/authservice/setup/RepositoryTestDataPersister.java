package io.hrushik09.authservice.setup;

import io.hrushik09.authservice.clients.Client;
import io.hrushik09.authservice.clients.ClientBuilder;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static io.hrushik09.authservice.clients.ClientBuilder.aClient;

public class RepositoryTestDataPersister {
    public Client persistedClient(TestEntityManager entityManager, String pid, String clientId) {
        Client client = aClient().withId(null)
                .withPid(pid)
                .withClientId(clientId).build();
        return entityManager.persist(client);
    }

    public void persistedClient(TestEntityManager entityManager, ClientBuilder clientBuilder) {
        entityManager.persist(clientBuilder.build());
    }
}
