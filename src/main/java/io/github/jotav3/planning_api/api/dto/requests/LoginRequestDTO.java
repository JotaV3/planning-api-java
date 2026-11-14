package io.github.jotav3.planning_api.api.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "Enter your login.")
        String login,

        @NotBlank(message = "Enter the password.")
        String password
) {}
