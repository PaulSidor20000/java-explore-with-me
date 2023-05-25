package ru.practicum.stat.hit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.statdto.dto.RequestDto;
import ru.practicum.statdto.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HitService {
    private final HitMapper mapper;
    private final HitRepository hitRepository;
    private static final String SAVE_ANSWER = "Информация сохранена";

    public String saveRequest(RequestDto dto) {
        hitRepository.save(mapper.map(dto));

        return SAVE_ANSWER;
    }

    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        if (uris == null && !unique) {
            return hitRepository.findAllByTime(start, end);
        }

        if (uris != null && !unique) {
            return hitRepository.findAllByTimeAndUris(start, end, uris);
        }

        if (uris == null) {
            return hitRepository.findAllByTimeAndIp(start, end);
        }

        return hitRepository.findAllByTimeAndUriAndIp(start, end, uris);
    }

}
