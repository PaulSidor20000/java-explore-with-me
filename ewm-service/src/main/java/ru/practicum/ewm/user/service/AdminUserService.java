package ru.practicum.ewm.user.service;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;

@Service
public interface AdminUserService {
    Flux<UserDto> findUsers(MultiValueMap<String, String> params);

    Mono<UserDto> createUser(NewUserRequest dto);

    Mono<Void> deleteUser(int userId);

}
