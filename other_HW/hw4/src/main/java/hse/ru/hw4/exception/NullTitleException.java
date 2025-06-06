package hse.ru.hw4.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullTitleException extends RuntimeException {
    public NullTitleException(String message) {
        super(message);
    }
}
