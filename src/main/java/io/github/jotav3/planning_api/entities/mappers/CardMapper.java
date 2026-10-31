package io.github.jotav3.planning_api.entities.mappers;

import io.github.jotav3.planning_api.entities.Card;
import io.github.jotav3.planning_api.entities.dto.CardRequestDTO;
import io.github.jotav3.planning_api.entities.dto.CardResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {
    Card toEntity(CardRequestDTO dto);
    CardResponseDTO toCardResponseDTO(Card card);
}
