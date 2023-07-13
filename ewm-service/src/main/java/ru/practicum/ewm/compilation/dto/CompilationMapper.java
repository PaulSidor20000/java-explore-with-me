package ru.practicum.ewm.compilation.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.ewm.compilation.entity.Compilation;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CompilationMapper {

    @Mapping(target = "events", source = "dto.events")
    Compilation map(NewCompilationDto dto);

    Compilation merge(@MappingTarget Compilation compilation, UpdateCompilationRequest dto);

}
