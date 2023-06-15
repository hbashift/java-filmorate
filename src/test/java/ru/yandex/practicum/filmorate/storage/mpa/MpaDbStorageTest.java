package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class MpaDbStorageTest {
    private final MpaDbStorage mpaDbStorage;
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
    }

    @Test
    void findAllMpaTest() {
        LinkedHashSet<Mpa> mpaListTest = mpaDbStorage.getAllMpa();
        assertEquals(5, mpaListTest.size());
    }

    @Test
    void getMpaForIdTest() {
        Mpa mpaTest = mpaDbStorage.getMpaById(5);
        assertEquals("NC-17", mpaTest.getName());
    }

    @Test
    void addMpaInFilmTest() {
        assertNull(film.getMpa());
        film.setMpa(Mpa.builder()
                .id(1)
                .name("NC-17")
                .build());
        assertNotNull(film.getMpa());
    }
}