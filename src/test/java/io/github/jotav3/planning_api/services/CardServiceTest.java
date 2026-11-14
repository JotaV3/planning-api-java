package io.github.jotav3.planning_api.services;

import io.github.jotav3.planning_api.api.Card;
import io.github.jotav3.planning_api.api.dto.requests.CardRequestDTO;
import io.github.jotav3.planning_api.api.enums.CardBrand;
import io.github.jotav3.planning_api.api.services.exceptions.ResourceNotFoundException;
import io.github.jotav3.planning_api.api.services.exceptions.ValidationException;
import io.github.jotav3.planning_api.api.repositories.CardRepository;
import io.github.jotav3.planning_api.api.services.CardService;
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
