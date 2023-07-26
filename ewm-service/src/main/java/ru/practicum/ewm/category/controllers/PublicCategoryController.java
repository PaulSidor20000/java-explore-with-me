package ru.practicum.ewm.category.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.PublicCategoryService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {
    private final PublicCategoryService publicCategoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<CategoryDto> findCategories(@RequestParam(defaultValue = "0") Integer from,
                                            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable page = PageRequest.of(from, size);
        log.info("GET categories by params={}", page);

        return publicCategoryService.findCategories(page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CategoryDto> findCategoryById(@PathVariable Integer id) {
        log.info("GET category by id={}", id);

        return publicCategoryService.findCategoryById(id);
    }

}
