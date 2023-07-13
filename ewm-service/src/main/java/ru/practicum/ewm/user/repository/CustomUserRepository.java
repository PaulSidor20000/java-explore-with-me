package ru.practicum.ewm.user.repository;

import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.user.entity.User;

public interface CustomUserRepository {

    Flux<User> findAllUsersByParams(MultiValueMap<String, String> params);

}
