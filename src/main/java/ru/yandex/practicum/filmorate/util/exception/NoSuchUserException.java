package ru.yandex.practicum.filmorate.util.exception;

public class NoSuchUserException extends NoSuchModelException {
    public NoSuchUserException() {
        super();
    }

    public NoSuchUserException(String message) {
        super(message);
    }
}
