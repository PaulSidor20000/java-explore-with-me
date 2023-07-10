package ru.practicum.ewm.user.repository;

import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.user.dto.UserDto;

public interface CustomUserRepository {

    Flux<UserDto> findAllUsersByParams(MultiValueMap<String, String> params);

}
