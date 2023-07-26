package ru.practicum.ewm.event.dto;

import lombok.Data;
import ru.practicum.ewm.exceptions.BadRequestException;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class EventParams {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Float lon;
    private Float lat;

    @PositiveOrZero
    private Integer radius;

    private List<Integer> users;
    private List<Integer> categories;
    private List<EventState> states;
    private String rangeStart;
    private String rangeEnd;
    private Integer from;
    private Integer size;
    private Boolean paid;
    private Boolean onlyAvailable;
    private String text;
    private String sort;

    public EventParams() {
        if (rangeStart != null && rangeEnd != null) {
            LocalDateTime start = LocalDateTime.parse(rangeStart, formatter);
            LocalDateTime end = LocalDateTime.parse(rangeEnd, formatter);

            if (start.isAfter(end)) {
                throw new BadRequestException("Start time must be before end time");
            }
        }
    }



}
