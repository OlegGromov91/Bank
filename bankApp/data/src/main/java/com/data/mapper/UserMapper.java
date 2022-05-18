package com.data.mapper;

import com.data.dto.security.user.UserDto;
import com.data.model.security.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "sex", source = "sex")
    UserDto toDto(User user);

}
