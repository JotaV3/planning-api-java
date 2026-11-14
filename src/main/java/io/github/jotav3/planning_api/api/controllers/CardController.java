package io.github.jotav3.planning_api.api.controllers;

import io.github.jotav3.planning_api.api.dto.requests.CardRequestDTO;
import io.github.jotav3.planning_api.api.dto.responses.CardResponseDTO;
import io.github.jotav3.planning_api.api.services.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cards")
@CrossOrigin("*")
public class CardController {

    @Autowired
    private CardService service;

    @PostMapping
    public ResponseEntity<CardResponseDTO> create(@RequestBody @Valid CardRequestDTO requestDTO){
        CardResponseDTO responseDTO = service.create(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDTO> findById(@PathVariable UUID id){
        CardResponseDTO dto = service.findById(id);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponseDTO> update(@PathVariable UUID id, @RequestBody @Valid CardRequestDTO requestDTO){
        CardResponseDTO responseDTO = service.update(id, requestDTO);

        return ResponseEntity.ok().body(responseDTO);
    }
}
