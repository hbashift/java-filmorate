package ru.yandex.practicum.filmorate.util.exception;

public class FilmAlreadyExistsException extends AlreadyExistsException {
    public FilmAlreadyExistsException() {
        super();
    }

    public FilmAlreadyExistsException(String message) {
        super(message);
    }
}
