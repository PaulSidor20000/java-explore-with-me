package ru.practicum.ewm.request.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RequestRouter {

    @Bean
    public RouterFunction<ServerResponse> privateRequestRoutes(PrivateRequestHandler handler) {
        return RouterFunctions.route()
                .path("/users/{userId}/requests", builder ->
                        builder
                                .PATCH("/{requestId}/cancel", handler::cancelUserRequest)
                                .POST(handler::createNewRequest)
                                .GET(handler::findUserRequests))
                .build();

    }

}