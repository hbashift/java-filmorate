package ru.yandex.practicum.filmorate.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchFilmException extends NoSuchModelException {
    public NoSuchFilmException() {
        super();
    }

    public NoSuchFilmException(String e) {
        super(e);
    }
}
