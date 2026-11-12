package io.github.jotav3.planning_api.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.convert.Jsr310Converters;

import java.util.UUID;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;
}
