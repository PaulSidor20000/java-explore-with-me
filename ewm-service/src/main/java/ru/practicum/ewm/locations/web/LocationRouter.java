package ru.practicum.ewm.locations.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class LocationRouter {

//    @Bean
//    public RouterFunction<ServerResponse> adminLocationRoutes(AdminLocationHandler handler) {
//        return RouterFunctions.route()
//                .path("/admin/locations", builder ->
//                        builder
//                                .POST(handler::createLocation)
//                                .DELETE("/{locationsId}", handler::deleteLocation))
//                .build();
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> publicLocationRoutes(PublicLocationHandler handler) {
//        return RouterFunctions.route()
//                .path("/locations", builder ->
//                        builder
//                                .GET("/{locationsId}", handler::findLocationById)
//                                .GET(handler::findLocations))
//                .build();
//    }

}