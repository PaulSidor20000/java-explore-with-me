package ru.practicum.ewm.user;

import org.mapstruct.Mapper;
import ru.practicum.ewm.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(UserDto dto);

    UserDto map(User user);

}
