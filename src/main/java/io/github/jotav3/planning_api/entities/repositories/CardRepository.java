package io.github.jotav3.planning_api.entities.repositories;

import io.github.jotav3.planning_api.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    Optional<Card> findByName(String name);

    @Query("""
        SELECT c
        FROM Card c
        WHERE (:id is null or c.id != :id)
        AND c.name = :name
    """)
    List<Card> findByNameAndNotId(@Param("name") String name, @Param("id") UUID id);
}
