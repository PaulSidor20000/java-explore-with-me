package ru.practicum.ewm.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.user.entity.User;
import ru.practicum.ewm.utils.EwmUtils;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private final R2dbcEntityTemplate template;

    @Override
    public Flux<User> findAllUsersByParams(MultiValueMap<String, String> params) {
        return template.select(User.class)
                .from("users")
                .matching(
                        query(
                                Optional.ofNullable(params.get("ids"))
                                        .map(ids -> where("user_id").in(ids.stream()
                                                .map(Integer::parseInt)
                                                .collect(Collectors.toList())))
                                        .orElseGet(() -> where("user_id").isNotNull()))
                                .with(EwmUtils.getPage(params)))
                .all();
    }

}
