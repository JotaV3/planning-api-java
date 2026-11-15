package io.github.jotav3.planning_api.api.controllers;

import io.github.jotav3.planning_api.api.dtos.requests.LoginRequestDTO;
import io.github.jotav3.planning_api.api.dtos.requests.UserRequestDTO;
import io.github.jotav3.planning_api.api.dtos.responses.AuthResponseDTO;
import io.github.jotav3.planning_api.application.services.security.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthService service;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> signup(@Valid @RequestBody UserRequestDTO dto){
        AuthResponseDTO authResponseDTO = service.registerUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponseDTO);
    }

    @PostMapping("/signing")
    public AuthResponseDTO signing(@Valid @RequestBody LoginRequestDTO dto){
        return service.authenticateUser(dto);
    }
}
