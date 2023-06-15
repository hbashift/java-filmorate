package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.LinkedHashSet;
import java.util.List;

public interface FilmStorage {
    LinkedHashSet<Film> getFilms();

    Film addFilm(Film film);

    Film updateFilm(Film film);

    Film getFilmById(int id);

    Film addLike(int id, int userId);

    Film deleteLike(int id, int userId);

    List<Film> getTop(int count);
}
