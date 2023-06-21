package ru.practicum.ewm.request.dto;

import lombok.Data;

@Data
public class ParticipationRequestDto {

    private String created;

    private Integer event;

    private Integer id;

    private Integer requester;

    private RequestStatus status;

}
