package ru.job4j.dish.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Dish service backend API",
                description = "Dish service backend", version = "1.0.0",
                contact = @Contact(
                        name = "Denis Kalchenko",
                        email = "denfort50@yandex.ru",
                        url = "https://github.com/denfort50"
                )
        )
)
public class OpenApiConfig {
}
