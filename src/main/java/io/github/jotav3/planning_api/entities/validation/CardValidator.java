package io.github.jotav3.planning_api.entities.validation;

import io.github.jotav3.planning_api.entities.dto.CardRequestDTO;
import io.github.jotav3.planning_api.entities.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardValidator {

    @Autowired
    private CardRepository repository;

    public ValidationResult validateName(CardRequestDTO dto){
        var result = ValidationResult.newValidationResult();

        if(repository.findByName(dto.name()).isPresent()){
            result.add(new InvalidField("name", "already registered"));
        }

        return result;
    }
}
