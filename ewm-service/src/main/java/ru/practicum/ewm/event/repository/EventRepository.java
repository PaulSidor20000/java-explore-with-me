package ru.practicum.ewm.event.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.entity.Event;

@Repository
public interface EventRepository extends R2dbcRepository<Event, Integer>, CustomEventRepository {

    Mono<Event> findByIdAndAndUserId(int eventId, int userId);

}
