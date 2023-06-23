package ru.practicum.ewm.category.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.category.dto.CategoryDto;

@Service
public interface PublicCategoryService {

    Flux<CategoryDto> findCategories(int from, int size);

    Mono<CategoryDto> findCategoryById(int categoryId);

}
