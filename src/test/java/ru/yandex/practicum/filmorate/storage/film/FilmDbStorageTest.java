package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FilmDbStorageTest {
    private final FilmDbStorage filmDbStorage;
    private final FilmService filmService;
    private final UserDbStorage userDbStorage;
    Film film;
    Film film2;
    User user;
    User user2;

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
                .name("G")
                .build());

        film2 = Film.builder()
                .name("name2")
                .description("desc")
                .releaseDate(LocalDate.of(1999, 8, 17).format(DateTimeFormatter.ISO_DATE))
                .duration(136)
                .build();
        film2.setGenres(new HashSet<>());
        film2.setLikes(new HashSet<>());
        film2.setMpa(Mpa.builder()
                .id(1)
                .name("G")
                .build());

        user = User.builder()
                .email("mail@mail.mail")
                .login("login")
                .birthday(LocalDate.of(1999, 8, 17))
                .build();
        user.setFriends(new HashSet<>());

        user2 = User.builder()
                .email("gmail@gmail.gmail")
                .login("nelogin")
                .birthday(LocalDate.of(2001, 6, 19))
                .build();
        user2.setFriends(new HashSet<>());
    }

    @Test
    void addFilmTest() {
        filmDbStorage.addFilm(film);
        assertEquals(film, filmDbStorage.getFilmById(film.getId()));
    }

    @Test
    void updateFilmTest() {
        filmDbStorage.addFilm(film);
        assertEquals(film, filmDbStorage.getFilmById(film.getId()));

        film.setName("updateName");
        filmDbStorage.updateFilm(film);
        assertEquals("updateName", filmDbStorage.getFilmById(film.getId()).getName());
    }

    @Test
    void likeAndDeleteLikeTest() {
        filmDbStorage.addFilm(film);
        userDbStorage.addUser(user);
        userDbStorage.addUser(user2);
        filmDbStorage.addLike(1, 1);
        filmDbStorage.addLike(1, 2);
        film.setLikes(filmDbStorage.getFilmLikes(film.getId()));
        assertEquals(2, film.getLikes().size());

        filmDbStorage.deleteLike(1, 1);
        film.setLikes(filmDbStorage.getFilmLikes(film.getId()));
        assertEquals(1, film.getLikes().size());
    }

    @Test
    void getRatingTest() {
        filmDbStorage.addFilm(film);
        userDbStorage.addUser(user);
        userDbStorage.addUser(user2);
        filmDbStorage.addLike(1, 1);
        filmDbStorage.addLike(1, 2);
        assertEquals(1, filmService.getTop(1).get(0).getId());
    }
}