package io.hrushik09.authservice.authorities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    @Query("SELECT a FROM Authority a WHERE a.name = :name")
    Optional<Authority> findByName(String name);
}
