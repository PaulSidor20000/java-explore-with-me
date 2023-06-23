package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.category.reposytory.CategoryRepository;

@Service
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Flux<CategoryDto> findCategories(int from, int size) {
        return categoryRepository.findAllBy(getPage(from, size))
                .map(categoryMapper::map);
    }

    @Override
    public Mono<CategoryDto> findCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::map);
    }

    private Pageable getPage(int from, int size) {
        return PageRequest.of(from > 0 ? from / size : 0, size);
    }

}
