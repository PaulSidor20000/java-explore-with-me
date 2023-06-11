package ru.practicum.ewm.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> adminUserRoutes(AdminUserHandler handler) {
        return RouterFunctions.route()
                .path("/admin/users", builder ->
                        builder
                                .GET(handler::findUser)
                                .POST(handler::createUser)
                                .DELETE("/{id}", handler::deleteUser))
                .build();
    }

}