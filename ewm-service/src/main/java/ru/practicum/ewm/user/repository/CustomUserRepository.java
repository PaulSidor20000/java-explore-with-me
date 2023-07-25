package ru.practicum.ewm.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.user.entity.User;

import java.util.List;

public interface CustomUserRepository {

    Flux<User> findAllUsersByParams(List<Integer> ids, Pageable page);

}
