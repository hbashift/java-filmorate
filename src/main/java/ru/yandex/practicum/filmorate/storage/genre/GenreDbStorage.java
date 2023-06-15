package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.util.exception.NotFoundException;

import java.util.LinkedHashSet;

@Slf4j
@RequiredArgsConstructor
@Primary
@Component
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public LinkedHashSet<Genre> getGenres() {
        LinkedHashSet<Genre> genreList = new LinkedHashSet<>();
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("SELECT genre_id, name FROM GENRE");

        while (genreRows.next()) {
            Genre genre = Genre.builder()
                    .id(genreRows.getInt("genre_id"))
                    .name(genreRows.getString("name"))
                    .build();

            genreList.add(genre);
        }

        return genreList;
    }

    @Override
    public Genre getGenreById(int id) {
        String sqlQuery = "SELECT genre_id, name FROM GENRE WHERE genre_id=?";

        try {
            return jdbcTemplate.queryForObject(sqlQuery, new GenreRowMapper(), id);

        } catch (RuntimeException e) {
            throw new NotFoundException("Жанр не найден.");
        }
    }
}
