package io.github.jotav3.planning_api.api.services.security;

import io.github.jotav3.planning_api.api.User;
import io.github.jotav3.planning_api.api.dto.requests.UserRequestDTO;
import io.github.jotav3.planning_api.api.dto.responses.AuthResponseDTO;
import io.github.jotav3.planning_api.api.services.exceptions.ValidationException;
import io.github.jotav3.planning_api.api.mappers.UserMapper;
import io.github.jotav3.planning_api.api.repositories.UserRepository;
import io.github.jotav3.planning_api.api.services.validation.InvalidField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JWTService jwtService;

    public AuthResponseDTO registerUser(UserRequestDTO dto){
        if(userRepository.existsByLogin(dto.login())){
            throw new ValidationException(
                    List.of(new InvalidField("Login", "Login already exists."))
            );
        }

        User user = userMapper.toEntity(dto);

        // encrypts the user's password
        String encryptedPassword = encoder.encode(dto.password());
        user.setPassword(encryptedPassword);

        userRepository.save(user);

        return toAuthResponseDTO(user);
    }

    private AuthResponseDTO toAuthResponseDTO(User user){
        String token = jwtService.generateToken(user);
        long expiresAt = jwtService.getExpirationSeconds();

        return new AuthResponseDTO(
                token,
                "Bearer",
                expiresAt,
                user.getName()
        );
    }
}
