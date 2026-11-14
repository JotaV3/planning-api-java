package io.github.jotav3.planning_api.api.dto.responses;

import io.github.jotav3.planning_api.api.enums.CardBrand;

import java.time.LocalDateTime;

public record CardResponseDTO(String id, String name, CardBrand brand, LocalDateTime registrationDate) {
}
