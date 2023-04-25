package ru.yandex.practicum.filmorate.util.exception;

public class UserAlreadyExistsException extends AlreadyExistsException {
    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
