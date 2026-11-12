package io.github.jotav3.planning_api.entities.dto.responses;

public record AuthResponseDTO(
        String token,
        String type,
        long expiresIn,
        String userName
) {}
