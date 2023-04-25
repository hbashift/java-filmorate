package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.util.exception.NoSuchModelException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
class FilmService implements CommonService<Film> {
    private final Map<Integer, Film> films;
    private int id;

    public FilmService() {
        id = 0;
        films = new HashMap<>();
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film create(Film film) throws AlreadyExistsException {
        film.setId(++id);
        if (films.containsValue(film)) {
            throw new AlreadyExistsException("Such film already exists");
        }

        films.put(id, film);

        return film;
    }

    @Override
    public Film update(Film film) throws NoSuchModelException {
        if (!films.containsKey(film.getId())) {
            throw new NoSuchModelException("There is no such film");
        }

        films.put(film.getId(), film);

        return film;
    }
}
