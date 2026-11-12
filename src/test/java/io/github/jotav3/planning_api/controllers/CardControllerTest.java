package io.github.jotav3.planning_api.controllers;

import io.github.jotav3.planning_api.entities.Card;
import io.github.jotav3.planning_api.entities.dto.responses.CardResponseDTO;
import io.github.jotav3.planning_api.entities.enums.CardBrand;
import io.github.jotav3.planning_api.entities.mappers.CardMapper;
import io.github.jotav3.planning_api.entities.repositories.CardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

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

    @Test
    public void shouldSaveCard() throws Exception{
        CardResponseDTO dto = createCard();

        String json = """
                {
                    "name": "VisaCard",
                    "brand": "VISA"
                }
                """;

        ResultActions result = mockMvc.perform(post("/cards")
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
        CardResponseDTO card = createCard();

        String json = """
                {
                    "name": "VisaCardUpdated",
                    "brand": "VISA"
                }
                """;

        ResultActions result = mockMvc.perform(put("/cards/{id}", card.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(card.id()))
                .andExpect(jsonPath("$.name").value("VisaCardUpdated"))
                .andExpect(jsonPath("$.brand").value("VISA"))
                .andExpect(jsonPath("$.registrationDate").isNotEmpty());
    }

    private CardResponseDTO createCard(){
        Card card = new Card(null, "VisaCard", CardBrand.VISA, LocalDateTime.now());

        repository.save(card);

        return mapper.toCardResponseDTO(card);
    }
}
