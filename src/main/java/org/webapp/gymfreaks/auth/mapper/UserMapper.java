package org.webapp.gymfreaks.auth.mapper;

import org.mapstruct.Mapper;

import org.webapp.gymfreaks.auth.model.UserEntity;
import org.webapp.gymfreaks.auth.model.dto.user.UserViewDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserViewDto account);

    UserViewDto toDto(UserEntity account);

}
