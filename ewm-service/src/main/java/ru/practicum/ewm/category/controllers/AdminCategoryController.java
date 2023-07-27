package ru.practicum.ewm.category.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.service.AdminCategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CategoryDto> createCategory(@Valid @RequestBody NewCategoryDto dto) {
        log.info("POST new category={}", dto);

        return adminCategoryService.createCategory(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCategory(@PathVariable Integer id) {
        log.info("DELETE delete category id={}", id);

        return adminCategoryService.deleteCategory(id);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CategoryDto> updateCategory(@Valid @RequestBody NewCategoryDto dto,
                                            @PathVariable Integer id
    ) {
        log.info("PATCH update category id={}, category={}", id, dto);

        return adminCategoryService.updateCategory(id, dto);
    }

}
