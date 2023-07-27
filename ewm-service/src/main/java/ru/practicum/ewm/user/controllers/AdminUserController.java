package ru.practicum.ewm.user.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.service.AdminUserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<UserDto> findUsers(@RequestParam(required = false) List<Integer> ids,
                                   @RequestParam(defaultValue = "0") Integer from,
                                   @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable page = PageRequest.of(from, size);
        log.info("GET users by ids={}, params={}", ids, page);

        return adminUserService.findUsers(ids, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDto> createUser(@Valid @RequestBody NewUserRequest dto) {
        log.info("POST new user={}", dto);

        return adminUserService.createUser(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUser(@PathVariable Integer id) {
        log.info("DELETE user id={}", id);

        return adminUserService.deleteUser(id);
    }

}
