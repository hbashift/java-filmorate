package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.exception.AlreadyExistsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private Long id = 1L;

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film addFilm(Film film) {
        if (films.containsValue(film)) {
            log.warn("tried to add already existing film");

            throw new AlreadyExistsException("film with film_id:{" + id + "} already exists");
        }

        film.setId(id++);

        films.put(film.getId(), film);
        log.info("Film:{} added to the storage", film);

        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        films.put(film.getId(), film);
        log.info("Фильм с film_id:{} в хранилище обновлен", film.getId());

        return film;
    }

    @Override
    public Film getFilm(Long id) {
        return films.get(id);
    }

    @Override
    public Film addLike(Long id, Long userId) {
        films.get(id).getLikes().add(userId);
        log.info("Пользователь с user_id:{} поставил лайк фильму с film_id:{}", userId, id);

        return films.get(id);
    }

    @Override
    public Film deleteLike(Long id, Long userId) {
        films.get(id).getLikes().remove(userId);
        log.info("Пользователю с user_id:{} больше не нравится фильм с film_id:{}", userId, id);

        return films.get(id);
    }
}
