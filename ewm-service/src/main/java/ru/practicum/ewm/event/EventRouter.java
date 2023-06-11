package ru.practicum.ewm.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class EventRouter {

    @Bean
    public RouterFunction<ServerResponse> adminEventRoutes(AdminEventHandler handler) {
        return RouterFunctions.route()
                .path("/admin/events", builder ->
                        builder
                                .POST(handler::createEvent)
                                .PATCH("/{id}", handler::patchEvent))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> privateEventRoutes(PrivateEventHandler handler) {
        return RouterFunctions.route()
                .path("/users/{userId}/events", builder ->
                        builder
                                .GET(handler::findUserEvents)
                                .POST(handler::createUserEvent)
                                .GET("/{eventId}", handler::findUserEventById)
                                .PATCH("/{eventId}", handler::patchUserEventById)
                                .GET("/{eventId}/requests", handler::findRequestsOfUserEvent)
                                .PATCH("/{eventId}/requests", handler::patchRequestsOfUserEvent))
                .build();
    }

}