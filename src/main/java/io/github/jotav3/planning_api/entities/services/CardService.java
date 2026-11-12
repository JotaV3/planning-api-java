package io.github.jotav3.planning_api.entities.services;

import io.github.jotav3.planning_api.entities.Card;
import io.github.jotav3.planning_api.entities.dto.requests.CardRequestDTO;
import io.github.jotav3.planning_api.entities.dto.responses.CardResponseDTO;
import io.github.jotav3.planning_api.entities.exceptions.ResourceNotFoundException;
import io.github.jotav3.planning_api.entities.exceptions.ValidationException;
import io.github.jotav3.planning_api.entities.mappers.CardMapper;
import io.github.jotav3.planning_api.entities.repositories.CardRepository;
import io.github.jotav3.planning_api.entities.validation.CardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        var result = validator.validateName(dto, null);

        if(result.isInvalid()){
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

    @Transactional
    public CardResponseDTO update(UUID id, CardRequestDTO dto){
        Card card = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        var result = validator.validateName(dto, id);

        if(result.isInvalid()){
            throw new ValidationException(result.getInvalidFieldList());
        }

        mapper.update(card, dto);

        return mapper.toCardResponseDTO(card);
    }
}
