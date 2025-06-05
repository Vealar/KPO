package hse.ru.hw4.controller;

import hse.ru.hw4.exception.DuplicateTitleException;
import hse.ru.hw4.exception.NotFoundItemException;
import hse.ru.hw4.exception.NullTitleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler {

    @ExceptionHandler(NullTitleException.class)
    public ResponseEntity<String> handleNullTitleException(NullTitleException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundItemException.class)
    public ResponseEntity<String> handleNotFoundItemException(NotFoundItemException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateTitleException.class)
    public ResponseEntity<String> handleDuplicateTitleException(DuplicateTitleException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
