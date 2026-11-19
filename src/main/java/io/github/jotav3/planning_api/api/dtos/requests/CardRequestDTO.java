package io.github.jotav3.planning_api.api.dtos.requests;

import io.github.jotav3.planning_api.model.entities.enums.CardBrand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CardRequestDTO(
        @NotBlank(message = "Required field.")
        String name,

        @NotNull(message = "Required field.")
        CardBrand brand
) {}
