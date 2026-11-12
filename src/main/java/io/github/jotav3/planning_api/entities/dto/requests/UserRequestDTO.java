package io.github.jotav3.planning_api.entities.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "Login is required.")
        String login,

        @NotBlank(message = "Password is required.")
        @Size(min = 8, message = "Password must be at least 6 characters long.")
        String password,

        @NotBlank(message = "Name is required.")
        String name
)
{}
