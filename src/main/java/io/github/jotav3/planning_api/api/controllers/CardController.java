package io.github.jotav3.planning_api.api.controllers;

import io.github.jotav3.planning_api.api.dtos.requests.CardRequestDTO;
import io.github.jotav3.planning_api.api.dtos.responses.CardResponseDTO;
import io.github.jotav3.planning_api.application.services.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
@CrossOrigin("*")
public class CardController {

    @Autowired
    private CardService service;

    @PostMapping
    public ResponseEntity<CardResponseDTO> create(
            @RequestBody @Valid CardRequestDTO requestDTO,
            @AuthenticationPrincipal Jwt jwt
    ){
        // gets the user ID from JWT subject
        UUID userId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));

        CardResponseDTO responseDTO = service.create(requestDTO, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDTO> findByIdAndUserId(
            @PathVariable UUID id,
            @AuthenticationPrincipal Jwt jwt
    ){
        UUID userId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));

        CardResponseDTO dto = service.findByIdAndUserId(id, userId);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid CardRequestDTO requestDTO,
            @AuthenticationPrincipal Jwt jwt
    ){
        // gets the user ID from JWT subject
        UUID userId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));

        CardResponseDTO responseDTO = service.update(id, requestDTO, userId);

        return ResponseEntity.ok().body(responseDTO);
    }
}
