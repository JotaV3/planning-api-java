package io.github.jotav3.planning_api.entities.repositories;

import io.github.jotav3.planning_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
}
