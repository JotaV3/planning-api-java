package io.github.jotav3.planning_api.entities.services;

import io.github.jotav3.planning_api.entities.Card;
import io.github.jotav3.planning_api.entities.dto.CardRequestDTO;
import io.github.jotav3.planning_api.entities.dto.CardResponseDTO;
import io.github.jotav3.planning_api.entities.exceptions.ResourceNotFoundException;
import io.github.jotav3.planning_api.entities.exceptions.ValidationException;
import io.github.jotav3.planning_api.entities.mappers.CardMapper;
import io.github.jotav3.planning_api.entities.repositories.CardRepository;
import io.github.jotav3.planning_api.entities.validation.CardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    @Autowired
    private CardMapper mapper;

    @Autowired
    private CardValidator validator;

    public CardResponseDTO create(CardRequestDTO dto){
        var result = validator.validateName(dto);

        if(!result.isValid()){
            throw new ValidationException(result.getInvalidFieldList());
        }

        Card card = mapper.toEntity(dto);
        repository.save(card);

        return mapper.toCardResponseDTO(card);
    }

    public CardResponseDTO findById(UUID id){
        return repository.findById(id)
                .map(mapper::toCardResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }
}
