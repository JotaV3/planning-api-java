package io.github.jotav3.planning_api.application.services;

import io.github.jotav3.planning_api.infra.repositories.UserRepository;
import io.github.jotav3.planning_api.model.entities.Card;
import io.github.jotav3.planning_api.api.dtos.requests.CardRequestDTO;
import io.github.jotav3.planning_api.api.dtos.responses.CardResponseDTO;
import io.github.jotav3.planning_api.application.services.exceptions.ResourceNotFoundException;
import io.github.jotav3.planning_api.application.services.exceptions.ValidationException;
import io.github.jotav3.planning_api.api.mappers.CardMapper;
import io.github.jotav3.planning_api.infra.repositories.CardRepository;
import io.github.jotav3.planning_api.application.services.validation.CardValidator;
import io.github.jotav3.planning_api.model.entities.User;
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

    @Autowired
    private UserRepository userRepository;

    public CardResponseDTO create(CardRequestDTO dto, UUID userId){
        var result = validator.validateName(dto, null, userId);

        if(result.isInvalid()){
            throw new ValidationException(result.getInvalidFieldList());
        }

        Card card = mapper.toEntity(dto);
        card.setUser(getUserByUserId(userId)); // get user and set it to card

        repository.save(card);

        return mapper.toCardResponseDTO(card);
    }

    public CardResponseDTO findByIdAndUserId(UUID id, UUID userId){
        return repository.findByIdAndUserId(id, userId)
                .map(mapper::toCardResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    @Transactional
    public CardResponseDTO update(UUID id, CardRequestDTO dto, UUID userId){
        Card card = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        var result = validator.validateName(dto, id, userId);

        if(result.isInvalid()){
            throw new ValidationException(result.getInvalidFieldList());
        }

        mapper.update(card, dto);

        return mapper.toCardResponseDTO(card);
    }

    private User getUserByUserId(UUID userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }
}
