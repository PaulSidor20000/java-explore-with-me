package ru.practicum.stat.hit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statdto.dto.RequestDto;
import ru.practicum.statdto.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class HitController {
    private final HitService statService;
    private static final String HIT_URI = "/hit";
    private static final String STATS_URI = "/stats";

    @PostMapping(HIT_URI)
    public String saveRequest(@RequestBody RequestDto dto) {
        log.info("POST for new RequestDto {}", dto);
        return statService.saveRequest(dto);
    }

    @GetMapping(STATS_URI)
    public List<ViewStats> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                    @RequestParam(required = false) String[] uris,
                                    @RequestParam(required = false, defaultValue = "false") boolean unique
    ) {
        log.info("GET stats, start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return statService.getStats(start, end, uris, unique);
    }

}
