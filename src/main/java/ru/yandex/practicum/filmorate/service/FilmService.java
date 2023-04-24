package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.exception.FilmAlreadyExistsException;
import ru.yandex.practicum.filmorate.util.exception.NoSuchFilmException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
class FilmService implements Service<Film> {
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
    public Film create(@Valid Film film) throws FilmAlreadyExistsException {
        film.setId(++id);
        if (films.containsValue(film)) {
            throw new FilmAlreadyExistsException("Such film already exists");
        }

        films.put(id, film);

        return film;
    }

    @Override
    public Film update(@Valid Film film) throws NoSuchFilmException {
        if (!films.containsKey(film.getId())) {
            throw new NoSuchFilmException("There is no such film");
        }

        films.put(film.getId(), film);

        return film;
    }
}
