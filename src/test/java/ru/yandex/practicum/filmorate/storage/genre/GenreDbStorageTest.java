package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.film.GenreService;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GenreDbStorageTest {
    private final FilmDbStorage filmDbStorage;
    private final GenreService genreService;
    Film film;

    @BeforeEach
    void setUp() {
        film = Film.builder()
                .name("name")
                .description("desc")
                .releaseDate(LocalDate.of(1999, 8, 17).format(DateTimeFormatter.ISO_DATE))
                .duration(136)
                .build();
        film.setGenres(new HashSet<>());
        film.setLikes(new HashSet<>());
        film.setMpa(Mpa.builder()
                .id(1)
                .name("NC-17")
                .build());
    }

    @Test
    void findAllGenreTest() {
        LinkedHashSet<Genre> genreListTest = genreService.getGenres();
        assertEquals(6, genreListTest.size());
    }

    @Test
    void setFilmGenreTest() {
        assertTrue(film.getGenres().isEmpty());
        film.getGenres().add(Genre.builder()
                .id(1)
                .name("Комедия")
                .build());
        assertEquals(1, film.getGenres().size());
    }

    @Test
    void getGenreForIdTest() {
        Genre genreTest = genreService.getGenreById(1);
        assertEquals("Комедия", genreTest.getName());
    }

    @Test
    void addGenreTest() {
        assertTrue(film.getGenres().isEmpty());
        filmDbStorage.addFilm(film);
        film.getGenres().add(Genre.builder()
                .id(1)
                .name("Комедия")
                .build());
        assertEquals(1, film.getGenres().size());
    }

    @Test
    void updateGenreTest() {
        assertTrue(film.getGenres().isEmpty());
        filmDbStorage.addFilm(film);
        film.getGenres().add(Genre.builder()
                .id(1)
                .name("Комедия")
                .build());
        assertEquals(1, film.getGenres().size());
    }
}