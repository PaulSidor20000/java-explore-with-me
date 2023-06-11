package ru.practicum.ewm.dto;

import lombok.Data;

@Data
public class ParticipationRequestDto {

    private String created;

    private int event;

    private int id;

    private int requester;

    private String status; // RequestState

}
