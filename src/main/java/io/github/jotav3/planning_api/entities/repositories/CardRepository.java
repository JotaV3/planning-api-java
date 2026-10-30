package io.github.jotav3.planning_api.entities.repositories;

import io.github.jotav3.planning_api.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
}
