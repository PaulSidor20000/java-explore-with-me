package ru.practicum.stat.hit.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.practicum.stat.hit.entity.HitEntity;
import ru.practicum.statdto.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface HitRepository extends R2dbcRepository<HitEntity, Long> {

    @Query(
            "select hit.app as app, hit.uri as uri, count(hit.uri) as hits" +
                    " from endpoint_hits as hit" +
                    " where hit.timestamp_at between :start and :end" +
                    " group by hit.uri, hit.app" +
                    " order by count(hit.uri) desc"
    )
    Flux<ViewStats> findStatsByTime(LocalDateTime start,
                                    LocalDateTime end
    );

    @Query(
            "select hit.app as app, hit.uri as uri, count(hit.uri) as hits" +
                    " from endpoint_hits as hit" +
                    " where hit.timestamp_at between :start and :end" +
                    " and hit.uri in (:uris)" +
                    " group by hit.uri, hit.app" +
                    " order by count(hit.uri) desc"
    )
    Flux<ViewStats> findStatsByTimeAndUris(LocalDateTime start,
                                           LocalDateTime end,
                                           Collection<String> uris
    );

    @Query(
            "select hit.app as app, hit.uri as uri, count(distinct(hit.ip)) as hits" +
                    " from endpoint_hits as hit" +
                    " where hit.timestamp_at between :start and :end" +
                    " group by hit.uri, hit.app" +
                    " order by count(hit.uri) desc"
    )
    Flux<ViewStats> findStatsByTimeAndIp(LocalDateTime start,
                                         LocalDateTime end
    );

    @Query(
            "select hit.app as app, hit.uri as uri, count(distinct(hit.ip)) as hits" +
                    " from endpoint_hits as hit" +
                    " where hit.timestamp_at between :start and :end" +
                    " and hit.uri in (:uris)" +
                    " group by hit.uri, hit.app" +
                    " order by count(hit.uri) desc"
    )
    Flux<ViewStats> findStatsByTimeAndUriAndIp(LocalDateTime start,
                                               LocalDateTime end,
                                               Collection<String> uris
    );

}
