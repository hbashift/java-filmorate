package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.CommonService;
import ru.yandex.practicum.filmorate.service.ServiceFactory;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final CommonService<Film> service = ServiceFactory.getFilmService();

    @GetMapping
    public List<Film> getFilms() {
        log.info("GET /films {}", service.getAll());

        return service.getAll();
    }

    @PostMapping
    public Film newFilm(final @Valid @RequestBody Film film) {
        Film output = service.create(film);
        log.info("POST /films {}", output);

        return output;
    }

    @PutMapping
    public Film updateFilm(final @Valid @RequestBody Film film) {
        Film output = service.update(film);
        log.info("PUT /films {}", output);

        return output;
    }
}
