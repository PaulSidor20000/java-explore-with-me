package ru.practicum.ewm.category;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CategoryRepository extends R2dbcRepository<Category, Integer> {

    Flux<Category> findAllBy(Pageable page);
}
