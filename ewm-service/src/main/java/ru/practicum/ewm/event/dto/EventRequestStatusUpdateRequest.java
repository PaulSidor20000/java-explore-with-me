package ru.practicum.ewm.event.dto;

import lombok.Data;
import ru.practicum.ewm.request.dto.RequestStatus;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequest {

    private List<Integer> requestIds;

    private RequestStatus status;
}
