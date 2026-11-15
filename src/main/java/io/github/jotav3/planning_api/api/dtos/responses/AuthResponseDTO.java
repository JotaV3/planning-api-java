package io.github.jotav3.planning_api.api.dtos.responses;

public record AuthResponseDTO(
        String token,
        String type,
        long expiresAt,
        String userName
) {}
