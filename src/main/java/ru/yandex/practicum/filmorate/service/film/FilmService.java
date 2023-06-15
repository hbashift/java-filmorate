package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.util.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.util.exception.NotFoundException;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;

    public LinkedHashSet<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film addFilm(Film film) throws AlreadyExistsException {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) throws NotFoundException {
        if (Objects.isNull(filmStorage.getFilmById(film.getId()))) {
            log.warn("tried to update non-existing film");

            throw new NotFoundException("There is no such film");
        }

        return filmStorage.updateFilm(film);
    }

    public Film getFilm(int id) throws NotFoundException {
        Film film = filmStorage.getFilmById(id);

        if (Objects.isNull(film)) {
            log.warn("no film with film_id:{}", id);

            throw new NotFoundException("no film with film_id:{" + id + "}");
        }

        return film;
    }

    public Film addLike(int filmId, int userId) throws NotFoundException {
        Film film;

        try {
            film = filmStorage.addLike(filmId, userId);
        } catch (NullPointerException e) {
            log.warn("tried to add Like to non-existing film or wrong user_id input");

            throw new NotFoundException("wrong user_id or film_id");
        }

        return film;
    }

    public Film deleteLike(int filmId, int userId) {
        Film film;

        try {
            film = filmStorage.deleteLike(filmId, userId);
        } catch (NullPointerException e) {
            log.warn("tried to delete Like to non-existing film or wrong user_id input");

            throw new NotFoundException("wrong user_id or film_id");
        }

        return film;
    }

    public List<Film> getTop(int count) {
        return filmStorage.getFilms().stream()
                .sorted((film, t1) -> t1.getLikes().size() - film.getLikes().size())
                .limit(count)
                .collect(Collectors.toList()
                );
    }
}
