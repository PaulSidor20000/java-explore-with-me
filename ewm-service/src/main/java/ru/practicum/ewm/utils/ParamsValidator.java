package ru.practicum.ewm.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;
import ru.practicum.ewm.event.dto.EventParams;
import ru.practicum.ewm.exceptions.BadRequestException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParamsValidator {
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//    public static void validateParams(EventParams params) {
//        if (params.get("rangeStart") != null && params.get("rangeEnd") != null) {
//            LocalDateTime start = LocalDateTime.parse(params.get("rangeStart").get(0), formatter);
//            LocalDateTime end = LocalDateTime.parse(params.get("rangeEnd").get(0), formatter);
//
//            if (start.isAfter(end)) {
//                throw new BadRequestException("Start time must be before end time");
//            }
//        }
//        if (params.get("radius") != null && Integer.parseInt(params.get("radius").get(0)) < 0) {
//            throw new BadRequestException("Radius must be positive");
//        }
//    }

}
