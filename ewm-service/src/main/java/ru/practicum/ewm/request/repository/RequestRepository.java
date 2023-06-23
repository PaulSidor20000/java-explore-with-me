package ru.practicum.ewm.request.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.request.dto.RequestStatus;
import ru.practicum.ewm.request.entity.Request;

@Repository
public interface RequestRepository extends R2dbcRepository<Request, Integer> {

    Mono<Integer> countByEventAndStatus(int eventId, RequestStatus status);

    Mono<Request> findByRequesterAndEvent(int userId, int eventId);

    Flux<Request> findByRequester(int userId);

    Mono<Request> findByRequesterAndId(int userId, int requestId);

}
