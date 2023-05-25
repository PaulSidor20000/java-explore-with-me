package ru.practicum.stat.hit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.statdto.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<HitEntity, Long> {

    @Query(
            "select new ru.practicum.statdto.dto.ViewStats(hit.app, hit.uri, count(hit.uri))" +
                    " from HitEntity as hit" +
                    " where hit.timestamp between :start and :end" +
                    " group by hit.uri" +
                    " order by count(hit.uri) desc"
    )
    List<ViewStats> findAllByTime(@Param("start") LocalDateTime start,
                                  @Param("end") LocalDateTime end
    );

    @Query(
            "select new ru.practicum.statdto.dto.ViewStats(hit.app, hit.uri, count(hit.uri)) " +
                    " from HitEntity as hit" +
                    " where hit.timestamp between :start and :end" +
                    " and hit.uri in :uris" +
                    " group by hit.uri" +
                    " order by count(hit.uri) desc"
    )
    List<ViewStats> findAllByTimeAndUris(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end,
                                         @Param("uris") String[] uris
    );

    @Query(
            "select new ru.practicum.statdto.dto.ViewStats(hit.app, hit.uri, count(distinct(hit.ip)))" +
                    " from HitEntity as hit" +
                    " where hit.timestamp between :start and :end" +
                    " group by hit.uri" +
                    " order by count(hit.uri) desc"
    )
    List<ViewStats> findAllByTimeAndIp(@Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end
    );

    @Query(
            "select new ru.practicum.statdto.dto.ViewStats(hit.app, hit.uri, count(distinct(hit.ip)))" +
                    " from HitEntity as hit" +
                    " where hit.timestamp between :start and :end" +
                    " and hit.uri in :uris" +
                    " group by hit.uri" +
                    " order by count(hit.uri) desc"
    )
    List<ViewStats> findAllByTimeAndUriAndIp(@Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end,
                                             @Param("uris") String[] uris
    );

}
