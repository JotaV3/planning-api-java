package io.github.jotav3.planning_api.model.entities;

import io.github.jotav3.planning_api.model.entities.enums.CardBrand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "tb_card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @Enumerated(EnumType.STRING)
    private CardBrand brand;
    private LocalDateTime registrationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Card(){

    }

    public Card(UUID id, String name, CardBrand brand, LocalDateTime registrationDate, User user) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.registrationDate = registrationDate;
        this.user = user;
    }

    @PrePersist
    private void prePersist(){
        setRegistrationDate(LocalDateTime.now());
    }
}
