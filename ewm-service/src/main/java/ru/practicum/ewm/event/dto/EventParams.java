package ru.practicum.ewm.event.dto;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;
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
    private List<String> states;
    private String rangeStart;
    private String rangeEnd;
    private Integer from;
    private Integer size;
    private Boolean paid;
    private Boolean onlyAvailable;
    private String text;
    private String sort;

}
