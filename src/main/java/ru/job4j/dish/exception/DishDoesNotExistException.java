package ru.job4j.dish.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DishDoesNotExistException extends RuntimeException {

    public DishDoesNotExistException(String message) {
        super(message);
    }
}
