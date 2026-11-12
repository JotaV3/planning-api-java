package io.github.jotav3.planning_api.entities.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotNull(message = "Login is required.")
        String login,

        @NotNull(message = "Password is required.")
        @Size(min = 8, message = "Password must be at least 6 characters long.")
        String password,

        @NotNull(message = "Name is required.")
        String name
)
{}
