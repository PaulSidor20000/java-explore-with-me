package ru.practicum.ewm.request.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.practicum.ewm.request.dto.RequestStatus;

@Getter
@Setter
@ToString
@Table(name = "requests")
public class Request {

    @Id
    private Integer id;

    private String created;

    @Column(value = "event_id")
    private Integer event;

    @Column(value = "user_id")
    private Integer requester;

    private RequestStatus status;

}
