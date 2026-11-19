package io.github.jotav3.planning_api.controllers;

import io.github.jotav3.planning_api.api.dtos.requests.UserRequestDTO;
import io.github.jotav3.planning_api.api.mappers.UserMapper;
import io.github.jotav3.planning_api.application.services.security.JWTService;
import io.github.jotav3.planning_api.infra.repositories.UserRepository;
import io.github.jotav3.planning_api.model.entities.Card;
import io.github.jotav3.planning_api.model.entities.User;
import io.github.jotav3.planning_api.model.entities.enums.CardBrand;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        ResultActions result = mockMvc
                .perform(post("/cards")
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

        Card card = createCard(user);

        String json = """
                {
                    "name": "VisaCardUpdated",
                    "brand": "VISA"
                }
                """;

        ResultActions result = mockMvc
                .perform(put("/cards/{id}", card.getId())
                        .header(AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(card.getId().toString()))
                .andExpect(jsonPath("$.name").value("VisaCardUpdated"))
                .andExpect(jsonPath("$.brand").value("VISA"))
                .andExpect(jsonPath("$.registrationDate").isNotEmpty());
    }

    @Test
    public void shouldReturn401WhenTokenIsMissing() throws Exception{
        // GET
        mockMvc.perform(get("/cards/{id}", UUID.randomUUID()))
                .andExpect(status().isUnauthorized());

        // POST
        mockMvc.perform(post("/cards"))
                .andExpect(status().isUnauthorized());

        // PUT
        mockMvc.perform(put("/cards"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldNotUpdateOtherUserCard() throws Exception{
        User user = createUser(); // user
        String token = jwtService.generateToken(user); // token owned by "user"

        User otherUser = createUser(); // otherUser
        Card otherUserCard = createCard(otherUser); // card owned by "otherUser"

        String json = """
                {
                    "name": "MyCardUpdated",
                    "brand": "VISA"
                }
                """;

        // it should not allow a user to update another user's card
        ResultActions result = mockMvc
                .perform(put("/cards/{id}", otherUserCard.getId()) // pass the card ID of the "otherUser"
                        .header(AUTHORIZATION, "Bearer " + token) // pass the token of "user"
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                );

        result
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotAllowFindOtherUserCard() throws Exception{
        User user = createUser(); // user
        String token = jwtService.generateToken(user); // token owned by "user"

        User otherUser = createUser(); // otherUser
        Card otherUserCard = createCard(otherUser); // card owned by "otherUser"

        ResultActions result = mockMvc
                .perform(get("/cards/{id}", otherUserCard.getId()) // pass the card ID of the "otherUser"
                        .header(AUTHORIZATION, "Bearer " + token) // pass the token of "user"
                );

        result
                .andExpect(status().isNotFound());
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
        Card card = new Card(
                null,
                "VisaCard",
                CardBrand.VISA,
                LocalDateTime.now(),
                user
        );

        return repository.save(card);
    }
}
