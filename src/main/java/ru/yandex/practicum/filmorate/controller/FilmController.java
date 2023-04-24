package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.NoSuchFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Set<Film> films = new HashSet<>();
    private int id = 0;

    @GetMapping
    public Set<Film> getFilms() {
        log.info("GET /films {}", films);

        return films;
    }

    @PostMapping
    public Film newFilm(final @Valid @RequestBody Film film) throws FilmAlreadyExistsException {
        film.setId(++id);
        if (films.stream()
                .anyMatch(f -> film.getDescription().equals(f.getDescription())
                        && film.getName().equals(f.getName())
                        && film.getReleaseDate().equals(f.getReleaseDate())
                        && film.getDuration().equals(f.getDuration()))) {

            log.warn("Trying to create already existing film");
            throw new FilmAlreadyExistsException("Such film already exists");
        }

        films.add(film);
        log.info("POST /films {}", film);

        return film;
    }

    @PutMapping
    public Film updateFilm(final @Valid @RequestBody Film film) throws NoSuchFilmException {
        if (!films.contains(film)) {
            log.warn("Trying to update non-existing film");
            throw new NoSuchFilmException("There is no such film");
        }

        films.remove(film);
        films.add(film);
        log.info("PUT /films {}", film);

        return film;
    }
}
