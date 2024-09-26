package ru.practicum.ewm.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.user.entity.User;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private final R2dbcEntityTemplate template;

    @Override
    public Flux<User> findAllUsersByParams(List<Integer> ids, Pageable page) {
        return template.select(User.class)
                .from("users")
                .matching(
                        query(
                                Optional.ofNullable(ids)
                                        .map(userIds -> where("user_id").in(userIds))
                                        .orElseGet(() -> where("user_id").isNotNull()))
                                .with(page))
                .all();
    }

}
