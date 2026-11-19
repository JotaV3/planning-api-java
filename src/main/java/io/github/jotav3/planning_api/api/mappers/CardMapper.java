package io.github.jotav3.planning_api.api.mappers;

import io.github.jotav3.planning_api.model.entities.Card;
import io.github.jotav3.planning_api.api.dtos.requests.CardRequestDTO;
import io.github.jotav3.planning_api.api.dtos.responses.CardResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface CardMapper {
    Card toEntity(CardRequestDTO dto);
    CardResponseDTO toCardResponseDTO(Card card);
    void update(@MappingTarget Card card, CardRequestDTO dto);
}
