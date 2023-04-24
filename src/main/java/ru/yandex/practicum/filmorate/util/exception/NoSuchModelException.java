package ru.yandex.practicum.filmorate.util.exception;

import java.util.NoSuchElementException;

public abstract class NoSuchModelException extends NoSuchElementException {
    public NoSuchModelException() {
        super();
    }

    public NoSuchModelException(String s) {
        super(s);
    }
}
