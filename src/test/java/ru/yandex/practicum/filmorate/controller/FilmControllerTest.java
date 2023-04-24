package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.serializer.DurationSerializer;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class FilmControllerTest {
    @Autowired
    private FilmController controller;

    @Autowired
    private MockMvc mvc;

    private static Gson gson;
    private static Film film;

    @BeforeAll
    public static void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationSerializer())
                .create();
    }

    @Test
    @Order(1)
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }


    @Test
    @Order(2)
    void shouldValidateNewFilms() throws Exception {
        film = new Film(1, "validNewFilm1", "desc", "1999-01-01", Duration.ofSeconds(200));

        mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(film)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gson.toJson(film)));

        film.setDuration(Duration.ofSeconds(-1000));

        mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(film)))
                .andExpect(status().isBadRequest());

        film.setReleaseDate("1200-02-02");

        mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    void shouldValidUpdateFilms() throws Exception {
        film = new Film(2, "validNewFilm2", "desc", "1999-01-01", Duration.ofSeconds(200));

        mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(film)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gson.toJson(film)));

        film.setName("newFilmName");

        mvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(film)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gson.toJson(film)));

        film = new Film(2, "nonValidFilm", "desc2",
                "1200-02-04", Duration.ofSeconds(200));

        mvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    void shouldReturnAllFilmsFormatted() throws Exception {
        film = new Film(3, "validNewFilm3", "desc", "1999-01-01", Duration.ofSeconds(200));

        mvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(film)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gson.toJson(film)));

        mvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(gson.toJson(film))));
    }
}