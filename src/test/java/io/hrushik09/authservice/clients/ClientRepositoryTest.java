package io.hrushik09.authservice.clients;

import io.hrushik09.authservice.setup.RepositoryTest;
import io.hrushik09.authservice.setup.RepositoryTestDataPersister;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static io.hrushik09.authservice.clients.ClientAuthorizationGrantTypeBuilder.aClientAuthorizationGrantType;
import static io.hrushik09.authservice.clients.ClientBuilder.aClient;
import static io.hrushik09.authservice.clients.ClientRedirectUriBuilder.aClientRedirectUri;
import static io.hrushik09.authservice.clients.ClientScopeBuilder.aClientScope;
import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class ClientRepositoryTest {
    private final RepositoryTestDataPersister having = new RepositoryTestDataPersister();
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ClientRepository clientRepository;

    @Test
    void validateExistsByPid() {
        Client client1 = having.persistedClient(entityManager, "pid1", "doesnt-matter-1");
        having.persistedClient(entityManager, "pid2", "doesnt-matter-2");
        entityManager.flush();
        entityManager.clear();

        boolean exists = clientRepository.existsByPid(client1.getPid());

        assertThat(exists).isTrue();
    }

    @Test
    void validateExistsByClientId() {
        Client client1 = having.persistedClient(entityManager, "doesnt-matter-1", "clientId1");
        having.persistedClient(entityManager, "doesnt-matter-2", "clientId2");
        entityManager.flush();
        entityManager.clear();

        boolean exists = clientRepository.existsByClientId(client1.getClientId());

        assertThat(exists).isTrue();
    }

    @Test
    void validateFindByPid() {
        String pid = "rc1";
        String clientId = "client1";
        String clientSecret = "secret";
        AuthenticationMethod authenticationMethod = AuthenticationMethod.CLIENT_SECRET_BASIC;
        ClientBuilder clientBuilder = aClient().withId(null)
                .withPid(pid)
                .withClientId(clientId)
                .withClientSecret(clientSecret)
                .withAuthenticationMethod(authenticationMethod)
                .with(aClientScope().withId(null).withValue("OPENID"))
                .with(aClientScope().withId(null).withValue("api:read"))
                .with(aClientScope().withId(null).withValue("api:create"))
                .with(aClientRedirectUri().withId(null).withValue("http://localhost:8080/authorized"))
                .with(aClientRedirectUri().withId(null).withValue("http://localhost:8080/api/authorized"))
                .with(aClientAuthorizationGrantType().withId(null).withValue(AuthorizationGrantType.AUTHORIZATION_CODE))
                .with(aClientAuthorizationGrantType().withId(null).withValue(AuthorizationGrantType.REFRESH_TOKEN));
        having.persistedClient(entityManager, clientBuilder);
        entityManager.flush();
        entityManager.clear();

        Optional<Client> optionalClient = clientRepository.findByPid(pid);

        assertThat(optionalClient).isPresent();
        Client fetched = optionalClient.get();
        assertThat(fetched.getId()).isNotNull();
        assertThat(fetched.getPid()).isEqualTo(pid);
        assertThat(fetched.getClientId()).isEqualTo(clientId);
        assertThat(fetched.getAuthenticationMethod()).isEqualTo(authenticationMethod);
        assertThat(fetched.getClientScopes()).hasSize(3);
        assertThat(fetched.getClientScopes()).extracting("value")
                .containsExactlyInAnyOrder("OPENID", "api:read", "api:create");
        assertThat(fetched.getClientRedirectUris()).hasSize(2);
        assertThat(fetched.getClientRedirectUris()).extracting("value")
                .containsExactlyInAnyOrder("http://localhost:8080/authorized", "http://localhost:8080/api/authorized");
        assertThat(fetched.getClientAuthorizationGrantTypes()).hasSize(2);
        assertThat(fetched.getClientAuthorizationGrantTypes()).extracting("value")
                .containsExactlyInAnyOrder(AuthorizationGrantType.AUTHORIZATION_CODE, AuthorizationGrantType.REFRESH_TOKEN);
    }
}