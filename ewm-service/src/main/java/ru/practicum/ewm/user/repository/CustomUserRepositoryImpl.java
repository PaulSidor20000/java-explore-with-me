package ru.practicum.ewm.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.entity.User;

import java.util.Optional;

import static java.lang.Integer.parseInt;
import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private final R2dbcEntityTemplate template;

    @Override
    public Flux<UserDto> findAllUsersByParams(MultiValueMap<String, String> params) {
        return template.select(User.class)
                .from("users")
                .as(UserDto.class)
                .matching(
                        query(
                                Optional.ofNullable(params.get("ids"))
                                        .map(ids -> where("id").in(ids))
                                        .orElseGet(() -> where("id").isNotNull()))
                                .with(getPage(params)))
                .all();
    }

    private Pageable getPage(MultiValueMap<String, String> params) {
        int from = params.get("from") != null ? parseInt(params.get("from").get(0)) : 0;
        int size = params.get("size") != null ? parseInt(params.get("size").get(0)) : 10;

        return PageRequest.of(from > 0 ? from / size : 0, size);
    }
}
