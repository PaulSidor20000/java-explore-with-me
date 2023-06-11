package ru.practicum.ewm.category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.practicum.ewm.dto.CategoryDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto map(Category category);

    Category map(CategoryDto dto);

    @Mapping(target = "id", ignore = true)
    Category merge(@MappingTarget Category category, CategoryDto dto);

}
