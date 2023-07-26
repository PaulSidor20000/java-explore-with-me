package ru.practicum.ewm.compilation.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminCompilationHandler {
//    private final DtoValidator validator;
//    private final AdminCompilationService service;
//    private static final String COMPILATION_ID = "compId";
//
//    public Mono<ServerResponse> createNewCompilation(ServerRequest request) {
//        return request.bodyToMono(NewCompilationDto.class)
//                .doOnNext(validator::validate)
//                .flatMap(service::createNewCompilation)
//                .flatMap(dto ->
//                        ServerResponse
//                                .status(HttpStatus.CREATED)
//                                .bodyValue(dto))
//                .onErrorResume(ErrorHandler::handler);
//    }
//
//    public Mono<ServerResponse> updateCompilation(ServerRequest request) {
//        int compilationId = Integer.parseInt(request.pathVariable(COMPILATION_ID));
//
//        return request.bodyToMono(UpdateCompilationRequest.class)
//                .doOnNext(dto -> {
//                    if (dto.getTitle() != null) {
//                        validator.validate(dto);
//                    }
//                })
//                .flatMap(dto -> service.updateCompilation(compilationId, dto))
//                .flatMap(dto ->
//                        ServerResponse
//                                .status(HttpStatus.OK)
//                                .bodyValue(dto))
//                .onErrorResume(ErrorHandler::handler);
//    }
//
//    public Mono<ServerResponse> deleteCompilation(ServerRequest request) {
//        int compilationId = Integer.parseInt(request.pathVariable(COMPILATION_ID));
//
//        return service.deleteCompilation(compilationId)
//                .then(ServerResponse
//                        .status(HttpStatus.NO_CONTENT)
//                        .body(Mono.just("Подборка удалена"), String.class))
//                .onErrorResume(ErrorHandler::handler);
//    }

}
