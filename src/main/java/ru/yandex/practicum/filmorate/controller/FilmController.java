package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.Service;
import ru.yandex.practicum.filmorate.service.ServiceFactory;
import ru.yandex.practicum.filmorate.util.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.util.exception.FilmAlreadyExistsException;
import ru.yandex.practicum.filmorate.util.exception.NoSuchFilmException;
import ru.yandex.practicum.filmorate.util.exception.NoSuchModelException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Service<Film> service = ServiceFactory.getFilmService();

    @GetMapping
    public List<Film> getFilms() {
        log.info("GET /films {}", service.getAll());

        return service.getAll();
    }

    @PostMapping
    public Film newFilm(final @Valid @RequestBody Film film) throws FilmAlreadyExistsException {
        Film output;
        try {
            output = service.create(film);
        } catch (AlreadyExistsException e) {
            log.warn("Trying to create already existing film");
            throw new FilmAlreadyExistsException("Such film already exists");
        }

        log.info("POST /films {}", output);

        return output;
    }

    @PutMapping
    public Film updateFilm(final @Valid @RequestBody Film film) throws NoSuchFilmException {
        Film output;
        try {
            output = service.update(film);
        } catch (NoSuchModelException e) {
            log.warn("Trying to update non-existing film");
            throw new NoSuchFilmException("There is no such film");
        }

        log.info("PUT /films {}", output);

        return output;
    }
}
