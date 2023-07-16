package ru.practicum.ewm.compilation.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.compilation.service.PublicCompilationService;
import ru.practicum.ewm.exceptions.ErrorHandler;

@Component
@RequiredArgsConstructor
public class PublicCompilationHandler {
    private final PublicCompilationService publicCompilationService;
    private static final String COMPILATION_ID = "compId";

    public Mono<ServerResponse> findCompilationById(ServerRequest request) {
        int compilationId = Integer.parseInt(request.pathVariable(COMPILATION_ID));

        return publicCompilationService.findCompilationById(compilationId)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> findCompilations(ServerRequest request) {
        return Mono.just(request.queryParams())
                .flatMapMany(publicCompilationService::findCompilations)
                .collectList()
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

}
