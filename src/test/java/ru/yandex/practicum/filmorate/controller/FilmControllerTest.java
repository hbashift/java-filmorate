package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.serializer.DurationSerializer;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {
    @Autowired
    private FilmController controller;

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationSerializer())
            .create();

    private static Film film;

    @BeforeAll
    public static void setUp() {
        film = new Film(1, "filmName", "description", "1999-12-12", Duration.ofSeconds(100));
    }

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }


    @Test
    void shouldValidateNewFilm() {
    }

    @Test
    void updateFilm() {
    }
}