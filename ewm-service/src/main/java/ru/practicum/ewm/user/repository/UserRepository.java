package ru.practicum.ewm.user.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.user.entity.User;

@Repository
public interface UserRepository extends R2dbcRepository<User, Integer>, CustomUserRepository {
}
