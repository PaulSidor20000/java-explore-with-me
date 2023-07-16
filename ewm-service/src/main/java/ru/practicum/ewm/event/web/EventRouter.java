package ru.practicum.ewm.event.web;

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
                                .GET(handler::findEvents)
                                .PATCH("/{eventId}", handler::updateEvent))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> publicEventRoutes(PublicEventHandler handler) {
        return RouterFunctions.route()
                .path("/events", builder ->
                        builder
                                .GET("/{eventId}", handler::findEventById)
                                .GET(handler::findEvents))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> privateEventRoutes(PrivateEventHandler handler) {
        return RouterFunctions.route()
                .path("/users/{userId}/events", builder ->
                        builder
                                .PATCH("/{eventId}/requests", handler::updateRequestsOfUserEvent)
                                .PATCH("/{eventId}", handler::updateUserEventById)
                                .GET("/{eventId}/requests", handler::findRequestsOfUserEvent)
                                .GET("/{eventId}", handler::findUserEventById)
                                .POST(handler::createNewEvent)
                                .GET(handler::findUserEvents))
                .build();
    }

}