package ru.yandex.practicum.filmorate.exception;

public class ValidationException extends Exception {
    public ValidationException() {
        super();
    }

    public ValidationException(String s) {
        super(s);
    }
}
