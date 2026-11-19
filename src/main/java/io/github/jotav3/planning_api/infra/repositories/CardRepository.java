package io.github.jotav3.planning_api.infra.repositories;

import io.github.jotav3.planning_api.model.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    @Query("""
        SELECT c
        FROM Card c
        WHERE c.id = :id
        AND c.user.id = :userId
    """)
    Optional<Card> findByIdAndUserId(
            @Param("id") UUID id,
            @Param("userId") UUID userId
    );

    @Query("""
        SELECT c
        FROM Card c
        WHERE (:id is null or c.id != :id)
        AND c.name = :name
        AND c.user.id = :userId
    """)
    List<Card> findByNameAndNotIdAndUserId(
            @Param("name") String name,
            @Param("id") UUID id,
            @Param("userId") UUID userId
    );
}
