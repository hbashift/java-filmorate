package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.util.exception.BadRequestException;
import ru.yandex.practicum.filmorate.util.exception.NoSuchModelException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<Film> getFilms() {

        log.info("GET /films");
        return filmService.getFilms();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("POST /films - film:{}", film);
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {

        log.info("PUT /films - film:{}", film.getName());
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable Long id, @PathVariable Long userId) {

        log.info("PUT /{}/like/{}", id, userId);
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        if (id < 0 || userId < 0) {
            log.warn("id is negative. expected positive");

            throw new NoSuchModelException("id is negative. expected positive");
        }

        log.info("DELETE /{}/like/{}", id, userId);
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) {

        log.info("GET film - /{}", id);
        return filmService.getFilm(id);
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") int count) {
        if (count < 0) {
            log.warn("Negative count");
            throw new BadRequestException("Count is negative: = " + count + ", expected positive");
        }

        log.info("GET top:{} /popular", count);
        return filmService.getTop(count);
    }
}