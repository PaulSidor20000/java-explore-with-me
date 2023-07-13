package ru.practicum.ewm.user.dto;

import org.mapstruct.Mapper;
import ru.practicum.ewm.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(NewUserRequest dto);

    UserDto map(User user);

}
