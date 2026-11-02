package io.github.jotav3.planning_api.services;

import io.github.jotav3.planning_api.entities.Card;
import io.github.jotav3.planning_api.entities.dto.CardRequestDTO;
import io.github.jotav3.planning_api.entities.enums.CardBrand;
import io.github.jotav3.planning_api.entities.exceptions.ResourceNotFoundException;
import io.github.jotav3.planning_api.entities.exceptions.ValidationException;
import io.github.jotav3.planning_api.entities.repositories.CardRepository;
import io.github.jotav3.planning_api.entities.services.CardService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class CardServiceTest {

    @Autowired
    private CardRepository repository;

    @Autowired
    private CardService service;

    @Test
    @Transactional
    public void shouldThrowValidationExceptionWhenCardNameExists(){
        String name = "DuplicatedCard";
        repository.save(new Card(null, name, CardBrand.VISA, LocalDateTime.now()));

        CardRequestDTO dto = new CardRequestDTO(name, CardBrand.VISA);

        assertThrows(ValidationException.class, () -> service.create(dto));
    }

    @Test
    public void shouldThrowResourceNotFoundException(){
        UUID id = UUID.randomUUID();

        assertThrows(ResourceNotFoundException.class, () -> service.findById(id));
    }
}
