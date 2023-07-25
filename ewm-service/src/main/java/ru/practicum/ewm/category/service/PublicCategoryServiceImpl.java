package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.category.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Flux<CategoryDto> findCategories(Pageable page) {
        return categoryRepository.findAllBy(page)
                .map(categoryMapper::map);
    }

    @Override
    public Mono<CategoryDto> findCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::map);
    }

}
