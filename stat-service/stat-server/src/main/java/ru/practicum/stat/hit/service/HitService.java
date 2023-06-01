package ru.practicum.stat.hit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.stat.hit.mapper.HitMapper;
import ru.practicum.stat.hit.repository.HitRepository;
import ru.practicum.statdto.dto.RequestDto;
import ru.practicum.statdto.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class HitService {
    private final HitMapper mapper;
    private final HitRepository hitRepository;
    private static final String SAVE_ANSWER = "Информация сохранена";

    public Mono<String> saveRequest(RequestDto dto) {
        return hitRepository.save(mapper.map(dto))
                .thenReturn(SAVE_ANSWER);
    }

    public Flux<ViewStats> getStats(LocalDateTime start, LocalDateTime end, Collection<String> uris, boolean unique) {
        if (uris == null && !unique) {
            return hitRepository.findStatsByTime(start, end);
        }

        if (uris != null && !unique) {
            return hitRepository.findStatsByTimeAndUris(start, end, uris);
        }

        if (uris == null) {
            return hitRepository.findStatsByTimeAndIp(start, end);
        }

        return hitRepository.findStatsByTimeAndUriAndIp(start, end, uris);
    }

}
