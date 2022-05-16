package ru.sberbank.local.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.sberbank.local.dto.security.user.UserDto;
import ru.sberbank.local.dto.security.user.UserLogonDTO;
import ru.sberbank.local.model.security.user.User;
import ru.sberbank.local.model.security.user.UserLogon;

@Mapper

public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "sex", source = "sex")
    UserDto toDto(User user);

}
