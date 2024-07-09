package org.webapp.gymfreaks.auth.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.webapp.gymfreaks.auth.model.UserEntity;
import org.webapp.gymfreaks.auth.model.dto.request.AccountUpdateDto;
import org.webapp.gymfreaks.auth.model.dto.user.UserViewDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true) // Ignore id during mapping to Entity
    UserEntity toEntity(UserViewDto account);

    UserViewDto toDto(UserEntity account);


    /**
     * @param  accountUpdateDto
     * @param  account
     * @return this for update an entity in database and ignore null value with keep the previous data
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity updateDtoToEntity(AccountUpdateDto accountUpdateDto, @MappingTarget UserEntity account);
}
