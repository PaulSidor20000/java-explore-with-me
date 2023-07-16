package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.CategoryConditionException;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public Mono<CategoryDto> createCategory(NewCategoryDto dto) {
        return Mono.just(categoryMapper.map(dto))
                .flatMap(categoryRepository::save)
                .map(categoryMapper::map);
    }

    @Override
    public Mono<Void> deleteCategory(int categoryId) {
        return eventRepository.findByCategoryId(categoryId)
                .doOnNext(this::doThrow)
                .then(categoryRepository.deleteById(categoryId));
    }

    @Override
    public Mono<CategoryDto> updateCategory(int categoryId, NewCategoryDto dto) {
        return categoryRepository.findById(categoryId)
                .map(category -> categoryMapper.merge(category, dto))
                .flatMap(categoryRepository::save)
                .map(categoryMapper::map);
    }

    private void doThrow(Event event) {
        if (event != null) {
            throw new CategoryConditionException(event);
        }
    }

}
