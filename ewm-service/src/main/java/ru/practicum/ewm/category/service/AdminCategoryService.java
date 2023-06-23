package ru.practicum.ewm.category.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;

@Service
public interface AdminCategoryService {

    Mono<CategoryDto> createCategory(NewCategoryDto dto);

    Mono<Void> deleteCategory(int categoryId);

    Mono<CategoryDto> updateCategory(int categoryId, NewCategoryDto dto);

}
