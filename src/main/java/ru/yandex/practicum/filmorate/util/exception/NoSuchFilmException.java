package ru.yandex.practicum.filmorate.util.exception;

public class NoSuchFilmException extends NoSuchModelException {
    public NoSuchFilmException() {
        super();
    }

    public NoSuchFilmException(String e) {
        super(e);
    }
}
