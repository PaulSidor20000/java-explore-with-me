package ru.practicum.stat.hit;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.statdto.dto.RequestDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
        componentModel = "spring",
        imports = {LocalDateTime.class, DateTimeFormatter.class}
)
public interface HitMapper {

    @Mapping(target = "timestamp", expression = "java(LocalDateTime.parse(dto.getTimestamp(), DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")))")
    HitEntity map(RequestDto dto);

}
