package io.github.jotav3.planning_api.entities.dto;

import io.github.jotav3.planning_api.entities.enums.CardBrand;

import java.time.LocalDateTime;

public record CardResponseDTO(String id, String name, CardBrand brand, LocalDateTime registrationDate) {
}
