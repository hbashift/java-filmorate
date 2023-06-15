package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.LinkedHashSet;

public interface GenreStorage {
    LinkedHashSet<Genre> getGenres();

    Genre getGenreById(int id);
}
