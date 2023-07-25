package ru.practicum.ewm.user.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

@Service
public interface AdminUserService {
    Flux<UserDto> findUsers(List<Integer> ids, Pageable page);

    Mono<UserDto> createUser(NewUserRequest dto);

    Mono<Void> deleteUser(int userId);

}
