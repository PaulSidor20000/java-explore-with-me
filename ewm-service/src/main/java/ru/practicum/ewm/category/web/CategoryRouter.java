package ru.practicum.ewm.category.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CategoryRouter {

    @Bean
    public RouterFunction<ServerResponse> adminCategoryRoutes(AdminCategoryHandler handler) {
        return RouterFunctions.route()
                .POST("/admin/categories", handler::createCategory)
                .DELETE("/admin/categories/{id}", handler::deleteCategory)
                .PATCH("/admin/categories/{id}", handler::updateCategory)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> publicCategoryRoutes(PublicCategoryHandler handler) {
        return RouterFunctions.route()
                .GET("/categories", handler::findCategories)
                .GET("/categories/{id}", handler::findCategoryById)
                .build();
    }

}