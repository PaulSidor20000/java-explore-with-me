package ru.practicum.ewm.category.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.practicum.ewm.category.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto map(Category category);

    Category map(NewCategoryDto dto);

    Category merge(@MappingTarget Category category, NewCategoryDto dto);

}
