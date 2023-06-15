package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.util.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.util.exception.NoSuchModelException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film addFilm(Film film) throws AlreadyExistsException {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) throws NoSuchModelException {
        if (Objects.isNull(filmStorage.getFilm(film.getId()))) {
            log.warn("tried to update non-existing film");

            throw new NoSuchModelException("There is no such film");
        }

        return filmStorage.updateFilm(film);
    }

    public Film getFilm(Long id) throws NoSuchModelException {
        Film film = filmStorage.getFilm(id);

        if (Objects.isNull(film)) {
            log.warn("no film with film_id:{}", id);

            throw new NoSuchModelException("no film with film_id:{" + id + "}");
        }

        return film;
    }

    public Film addLike(Long filmId, Long userId) throws NoSuchModelException {
        Film film;

        try {
            film = filmStorage.addLike(filmId, userId);
        } catch (NullPointerException e) {
            log.warn("tried to add like to non-existing film or wrong user_id input");

            throw new NoSuchModelException("wrong user_id or film_id");
        }

        return film;
    }

    public Film deleteLike(Long filmId, Long userId) {
        Film film;

        try {
            film = filmStorage.deleteLike(filmId, userId);
        } catch (NullPointerException e) {
            log.warn("tried to delete like to non-existing film or wrong user_id input");

            throw new NoSuchModelException("wrong user_id or film_id");
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

    public User getUser(Long userId) {
        User user;

        try {
            user = userStorage.getUser(userId);
        } catch (NullPointerException e) {
            log.warn("no user with user_id:{}", userId);

            throw new NoSuchModelException("no user with user_id:{" + userId + "}");
        }

        return user;
    }
}
