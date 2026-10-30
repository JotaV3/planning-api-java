package io.github.jotav3.planning_api.entities;

import io.github.jotav3.planning_api.entities.enums.CardBrand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @Enumerated(EnumType.STRING)
    private CardBrand brand;
    private LocalDateTime registrationDate;

    @PrePersist
    private void prePersist(){
        setRegistrationDate(LocalDateTime.now());
    }
}
