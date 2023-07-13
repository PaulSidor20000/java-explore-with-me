package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.exceptions.UserNotFoundException;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.ewm.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Flux<UserDto> findUsers(MultiValueMap<String, String> params) {
        return userRepository.findAllUsersByParams(params)
                .map(userMapper::map);
    }

    @Override
    public Mono<UserDto> createUser(NewUserRequest dto) {
        return Mono.just(userMapper.map(dto))
                .flatMap(userRepository::save)
                .map(userMapper::map);
    }

    @Override
    public Mono<Void> deleteUser(int userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException(userId)))
                .then(userRepository.deleteById(userId));
    }

}
