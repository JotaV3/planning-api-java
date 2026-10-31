package io.github.jotav3.planning_api.entities.controllers;

import io.github.jotav3.planning_api.entities.dto.CardRequestDTO;
import io.github.jotav3.planning_api.entities.dto.CardResponseDTO;
import io.github.jotav3.planning_api.entities.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService service;

    @PostMapping
    public ResponseEntity<CardResponseDTO> create(@RequestBody CardRequestDTO requestDTO){
        CardResponseDTO responseDTO = service.create(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
