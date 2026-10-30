package io.github.jotav3.planning_api.entities.dto;

import io.github.jotav3.planning_api.entities.enums.CardBrand;

public record CardRequestDTO(String name, CardBrand brand) {
}
