package io.github.jotav3.planning_api.entities.mappers;

import io.github.jotav3.planning_api.entities.User;
import io.github.jotav3.planning_api.entities.dto.requests.UserRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDTO dto);
}
