package io.github.jotav3.planning_api.entities.validation;

import io.github.jotav3.planning_api.entities.dto.CardRequestDTO;
import io.github.jotav3.planning_api.entities.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CardValidator {

    @Autowired
    private CardRepository repository;

    public ValidationResult validateName(CardRequestDTO dto, UUID id){
        var result = ValidationResult.newValidationResult();

        boolean isListNotEmpty = !repository.findByNameAndNotId(dto.name(), id).isEmpty();
        if(isListNotEmpty){
            result.add(new InvalidField("name", "Already registered."));
        }

        return result;
    }
}
