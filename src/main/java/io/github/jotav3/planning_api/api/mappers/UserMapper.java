package io.github.jotav3.planning_api.api.mappers;

import io.github.jotav3.planning_api.model.entities.User;
import io.github.jotav3.planning_api.api.dtos.requests.UserRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDTO dto);
}
