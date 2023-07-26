package ru.practicum.ewm.compilation.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CompilationRouter {

//    @Bean
//    public RouterFunction<ServerResponse> adminCompilationRoutes(AdminCompilationHandler handler) {
//        return RouterFunctions.route()
//                .path("/admin/compilations", builder ->
//                        builder
////                                .DELETE("/{compId}", handler::deleteCompilation))
////                                .PATCH("/{compId}", handler::updateCompilation)
////                                .POST(handler::createNewCompilation))
//                .build();
    }

//    @Bean
//    public RouterFunction<ServerResponse> publicCompilationRoutes(PublicCompilationHandler handler) {
//        return RouterFunctions.route()
//                .path("/compilations", builder ->
//                        builder
//                                .GET("/{compId}", handler::findCompilationById)
//                                .GET(handler::findCompilations))
//                .build();
//    }

//}