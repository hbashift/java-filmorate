package ru.yandex.practicum.filmorate.util.exception;

public abstract class AlreadyExistsException extends IllegalArgumentException {
    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}
