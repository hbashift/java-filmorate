package ru.yandex.practicum.filmorate.exception;

public abstract class AlreadyExistsException extends Exception {
    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}
