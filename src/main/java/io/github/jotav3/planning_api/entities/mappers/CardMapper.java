package io.github.jotav3.planning_api.entities.mappers;

import io.github.jotav3.planning_api.entities.Card;
import io.github.jotav3.planning_api.entities.dto.requests.CardRequestDTO;
import io.github.jotav3.planning_api.entities.dto.responses.CardResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CardMapper {
    Card toEntity(CardRequestDTO dto);
    CardResponseDTO toCardResponseDTO(Card card);
    void update(@MappingTarget Card card, CardRequestDTO dto);
}
