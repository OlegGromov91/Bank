package bank.data.mapper;

import bank.data.dto.security.user.UserDto;
import bank.data.model.security.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "sex", source = "sex")
    UserDto toDto(User user);

}
