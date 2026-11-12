package io.github.jotav3.planning_api.entities.dto.responses;

import java.util.UUID;

public record UserResponseDTO (
        UUID id,
        String login,
        String name
) {}
