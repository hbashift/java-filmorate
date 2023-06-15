package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.film.GenreService;

import java.util.LinkedHashSet;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    private final GenreService service;

    @GetMapping
    public LinkedHashSet<Genre> getGenres() {
        log.info("GET /genres");

        return service.getGenres();
    }

    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable("id") int genreId) {
        log.info("GET /genres/{}", genreId);

        return service.getGenreById(genreId);
    }
}
