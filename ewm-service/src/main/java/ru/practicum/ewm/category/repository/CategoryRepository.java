package ru.practicum.ewm.category.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.category.entity.Category;


@Repository
public interface CategoryRepository extends R2dbcRepository<Category, Integer> {

    Flux<Category> findAllBy(Pageable page);

}
