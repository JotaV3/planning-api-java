package io.github.jotav3.planning_api.api.mappers;

import io.github.jotav3.planning_api.api.User;
import io.github.jotav3.planning_api.api.dto.requests.UserRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDTO dto);
}
