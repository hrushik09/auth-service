package io.hrushik09.authservice.clients;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    boolean existsByPid(String pid);

    boolean existsByClientId(String clientId);

    Optional<Client> findByPid(String pid);
}
