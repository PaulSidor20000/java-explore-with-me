package ru.practicum.ewm.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.user.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends R2dbcRepository<User, Integer> {

    Flux<User> findAllBy(List<Integer> ids, Pageable page);

}
