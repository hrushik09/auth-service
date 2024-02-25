package io.hrushik09.authservice.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u JOIN FETCH u.authorities WHERE u.username = :username")
    Optional<User> findByUsername(String username);
}
