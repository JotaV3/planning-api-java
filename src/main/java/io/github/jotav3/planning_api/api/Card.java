package io.github.jotav3.planning_api.api;

import io.github.jotav3.planning_api.api.enums.CardBrand;
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

    public Card(){

    }

    public Card(UUID id, String name, CardBrand brand, LocalDateTime registrationDate) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.registrationDate = registrationDate;
    }

    @PrePersist
    private void prePersist(){
        setRegistrationDate(LocalDateTime.now());
    }
}
