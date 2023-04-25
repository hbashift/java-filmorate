package ru.yandex.practicum.filmorate.util.exception;

public class NoSuchModelException extends RuntimeException {
    public NoSuchModelException() {
        super();
    }

    public NoSuchModelException(String s) {
        super(s);
    }
}
