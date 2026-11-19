package io.github.jotav3.planning_api.services;

import io.github.jotav3.planning_api.api.dtos.requests.UserRequestDTO;
import io.github.jotav3.planning_api.api.mappers.UserMapper;
import io.github.jotav3.planning_api.infra.repositories.UserRepository;
import io.github.jotav3.planning_api.model.entities.Card;
import io.github.jotav3.planning_api.api.dtos.requests.CardRequestDTO;
import io.github.jotav3.planning_api.model.entities.User;
import io.github.jotav3.planning_api.model.entities.enums.CardBrand;
import io.github.jotav3.planning_api.application.services.exceptions.ResourceNotFoundException;
import io.github.jotav3.planning_api.application.services.exceptions.ValidationException;
import io.github.jotav3.planning_api.infra.repositories.CardRepository;
import io.github.jotav3.planning_api.application.services.CardService;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Test
    @Transactional
    public void shouldThrowValidationExceptionWhenCardNameExists(){
        User user = createUser();
        Card card = createCard(user); // original card
        repository.save(card);

        // other card gets the same name of the original card
        String otherCardName = card.getName();
        CardRequestDTO dto = new CardRequestDTO(otherCardName, CardBrand.VISA);

        assertThrows(ValidationException.class, () -> service.create(dto, card.getUser().getId()));
    }

    @Test
    public void shouldThrowResourceNotFoundException(){
        User user = createUser();

        // non-existent Card ID
        UUID id = UUID.randomUUID();

        assertThrows(ResourceNotFoundException.class, () ->
                service.findByIdAndUserId(id, user.getId()));
    }

    private User createUser(){
        String login = "MyLogin" + UUID.randomUUID();

        UserRequestDTO dto = new UserRequestDTO(
                login,
                "12345678",
                "UserTest"
        );

        return userRepository.save(userMapper.toEntity(dto));
    }

    private Card createCard(User user){
        String name = "MyCard" + UUID.randomUUID();

        return new Card(
                null,
                name,
                CardBrand.VISA,
                LocalDateTime.now(),
                user
        );


    }
}
