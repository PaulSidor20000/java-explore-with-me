package ru.practicum.ewm.dto;

import lombok.Data;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequest {

    private List<Integer> requestIds;

    private EventStatus status;
}
