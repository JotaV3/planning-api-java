package io.github.jotav3.planning_api.entities.services;

import io.github.jotav3.planning_api.entities.Card;
import io.github.jotav3.planning_api.entities.dto.CardRequestDTO;
import io.github.jotav3.planning_api.entities.dto.CardResponseDTO;
import io.github.jotav3.planning_api.entities.mappers.CardMapper;
import io.github.jotav3.planning_api.entities.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    @Autowired
    private CardMapper mapper;

    public CardResponseDTO create(CardRequestDTO dto){
        Card card = mapper.toEntity(dto);
        repository.save(card);

        return mapper.toCardResponseDTO(card);
    }
}
