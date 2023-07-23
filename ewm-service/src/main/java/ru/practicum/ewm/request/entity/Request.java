package ru.practicum.ewm.request.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.practicum.ewm.request.dto.RequestStatus;

@Getter
@Setter
@Table(name = "requests")
public class Request {

    @Id
    @Column(value = "request_id")
    private Integer id;

    @Column(value = "request_created")
    private String created;

    @Column(value = "event_id")
    private Integer event;

    @Column(value = "user_id")
    private Integer requester;

    @Column(value = "request_status")
    private RequestStatus status;

}
