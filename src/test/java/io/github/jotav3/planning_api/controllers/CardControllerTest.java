package io.github.jotav3.planning_api.controllers;

import io.github.jotav3.planning_api.api.dtos.requests.UserRequestDTO;
import io.github.jotav3.planning_api.api.mappers.UserMapper;
import io.github.jotav3.planning_api.application.services.security.JWTService;
import io.github.jotav3.planning_api.infra.repositories.UserRepository;
import io.github.jotav3.planning_api.model.entities.Card;
import io.github.jotav3.planning_api.api.dtos.responses.CardResponseDTO;
import io.github.jotav3.planning_api.model.entities.User;
import io.github.jotav3.planning_api.model.entities.enums.CardBrand;
import io.github.jotav3.planning_api.api.mappers.CardMapper;
import io.github.jotav3.planning_api.infra.repositories.CardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardRepository repository;

    @Autowired
    private CardMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JWTService jwtService;

    @Test
    public void shouldSaveCard() throws Exception{
        User user = createUser();
        String token = jwtService.generateToken(user);

        String json = """
                {
                    "name": "VisaCard",
                    "brand": "VISA"
                }
                """;

        ResultActions result = mockMvc.perform(post("/cards")
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        result
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("VisaCard"))
                .andExpect(jsonPath("$.brand").value("VISA"))
                .andExpect(jsonPath("$.registrationDate").isNotEmpty());
    }

    @Test
    public void shouldUpdateCard() throws Exception{
        User user = createUser();
        String token = jwtService.generateToken(user);

        CardResponseDTO card = createCard(user);

        String json = """
                {
                    "name": "VisaCardUpdated",
                    "brand": "VISA"
                }
                """;

        ResultActions result = mockMvc.perform(put("/cards/{id}", card.id())
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(card.id()))
                .andExpect(jsonPath("$.name").value("VisaCardUpdated"))
                .andExpect(jsonPath("$.brand").value("VISA"))
                .andExpect(jsonPath("$.registrationDate").isNotEmpty());
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

    private CardResponseDTO createCard(User user){
        Card card = new Card(
                null,
                "VisaCard",
                CardBrand.VISA,
                LocalDateTime.now(),
                user
        );

        repository.save(card);

        return mapper.toCardResponseDTO(card);
    }
}
