package io.github.jotav3.planning_api.api.dtos.responses;

import io.github.jotav3.planning_api.model.entities.enums.CardBrand;

import java.time.LocalDateTime;

public record CardResponseDTO(
        String id,
        String name,
        CardBrand brand,
        LocalDateTime registrationDate,
        UserResponseDTO user
) {}
