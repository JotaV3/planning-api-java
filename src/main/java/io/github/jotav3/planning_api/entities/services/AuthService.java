package io.github.jotav3.planning_api.entities.services;

import io.github.jotav3.planning_api.entities.User;
import io.github.jotav3.planning_api.entities.dto.requests.UserRequestDTO;
import io.github.jotav3.planning_api.entities.dto.responses.AuthResponseDTO;
import io.github.jotav3.planning_api.entities.exceptions.ValidationException;
import io.github.jotav3.planning_api.entities.mappers.UserMapper;
import io.github.jotav3.planning_api.entities.repositories.UserRepository;
import io.github.jotav3.planning_api.entities.validation.InvalidField;
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
        return new AuthResponseDTO(
                null,
                "Bearer",
                0,
                user.getName()
        );
    }
}
