package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.storage.mapper.MpaRowMapper;
import ru.yandex.practicum.filmorate.util.exception.NotFoundException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Primary
@Component
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public LinkedHashSet<Film> getFilms() {
        LinkedHashSet<Film> films = new LinkedHashSet<>();
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT film_id, name, description, release_date," +
                " duration, mpa_id FROM film");

        while (filmRows.next()) {
            Film film = rowSetMapper(filmRows);

            films.add(film);
        }

        return films;
    }

    @Override
    public Film addFilm(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film")
                .usingGeneratedKeyColumns("film_id");
        film.setId(simpleJdbcInsert.executeAndReturnKey(toMap(film)).intValue());

        setUpFilmGenres(film);

        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "UPDATE film SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? " +
                "WHERE film_id = ?";

        int rowsCount = jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());

        if (rowsCount == 0) {
            throw new NotFoundException("wrong film_id");
        }

        setUpFilmGenres(film);

        return film;
    }

    @Override
    public Film getFilmById(int id) {
        String sqlQuery = "SELECT film_id, name, description, release_date, duration, mpa_id " +
                "FROM film WHERE film_id=?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::resultFilmMapper, id);

        } catch (RuntimeException e) {
            throw new NotFoundException("wrong film_id");
        }
    }

    @Override
    public Film addLike(int id, int userId) {
        Film film = getFilmById(id);
        String sqlQuery = "INSERT INTO LIKES (film_id, user_id) VALUES(?, ?)";

        try {
            jdbcTemplate.update(sqlQuery, id, userId);
        } catch (RuntimeException e) {
            throw new NotFoundException("wrong film_id or user_id");
        }

        return film;
    }

    @Override
    public Film deleteLike(int id, int userId) {
        if (getUser(userId) == null) {
            throw new NotFoundException("Пользователь не найден.");
        }

        Film film = getFilmById(id);
        String sqlQuery = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sqlQuery, id, userId);

        return film;
    }

    @Override
    public List<Film> getTop(int count) {
        String sqlQuery = "SELECT *, COUNT(l.film_id) as count FROM film f \n" +
                "LEFT JOIN likes l ON f.film_id=l.film_id\n" +
                "GROUP BY f.film_id\n" +
                "ORDER BY count DESC\n" +
                "LIMIT ?";

        return jdbcTemplate.query(sqlQuery, this::resultFilmMapper, count);
    }

    protected User getUser(int userId) {
        String sqlQuery = "SELECT * FROM users WHERE user_id = ?";

        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sqlQuery, userId);

        if (userRows.next()) {
            return User.builder()
                    .id(userRows.getInt("user_id"))
                    .email(Objects.requireNonNull(userRows.getString("email")))
                    .login(Objects.requireNonNull(userRows.getString("login")))
                    .name(userRows.getString("name"))
                    .birthday(Objects.requireNonNull(userRows.getDate("birthday")).toLocalDate())
                    .build();
        } else {
            throw new NotFoundException();
        }
    }

    protected Set<Integer> getFilmLikes(int filmId) {
        String sqlQuery = "SELECT LIKE_ID FROM LIKES WHERE FILM_ID = ?";

        try {
            return new HashSet<>(jdbcTemplate.queryForList(sqlQuery, Integer.class, filmId));
        } catch (RuntimeException e) {
            throw new NotFoundException("wrong film_id");
        }
    }

    protected LinkedHashSet<Genre>
    getFilmGenres(int filmId) {
        String sqlQuery = "SELECT g.genre_id, g.name " +
                "FROM genre g INNER JOIN FILM_GENRE FG on g.GENRE_ID = FG.GENRE_ID " +
                "WHERE FG.FILM_ID = ?" +
                "ORDER BY g.GENRE_ID";

        return new LinkedHashSet<>(jdbcTemplate.query(sqlQuery, new GenreRowMapper(), filmId));
    }

    protected Mpa getFilmMpa(int filmId) {
        String sqlQuery = "SELECT m.name, m.MPA_ID " +
                "FROM mpa m " +
                "INNER JOIN FILM F on m.MPA_ID = F.MPA_ID " +
                "WHERE FILM_ID = ?";

        return jdbcTemplate.queryForObject(sqlQuery, new MpaRowMapper(), filmId);
    }

    protected Film resultFilmMapper(ResultSet rs, int rowNum) throws SQLException {
        return mapFilm(rs.getInt("film_id"), rs.getString("name"),
                rs.getString("description"), rs.getDate("release_date"),
                rs.getInt("duration"));
    }

    protected Film rowSetMapper(SqlRowSet rs) {
        return mapFilm(rs.getInt("film_id"), rs.getString("name"),
                rs.getString("description"), Objects.requireNonNull(rs.getDate("release_date")),
                rs.getInt("duration"));
    }

    protected Film mapFilm(int filmId, String name, String description, Date releaseDate, int duration) {
        return Film.builder()
                .id(filmId)
                .name(name)
                .description(description)
                .releaseDate(releaseDate
                        .toLocalDate().format(DateTimeFormatter.ISO_DATE))
                .duration(duration)
                .mpa(getFilmMpa(filmId))
                .likes(getFilmLikes(filmId))
                .genres(getFilmGenres(filmId))
                .build();
    }

    protected void setUpFilmGenres(Film film) {
        String sqlQuery = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
        insertInFilmGenre(film);
    }

    protected void insertInFilmGenre(Film film) {
        if (Objects.isNull(film.getGenres())) {
            return;
        }

        film.getGenres().forEach(g -> {
            String sqlQuery = "INSERT INTO FILM_GENRE(film_id, genre_id) VALUES (?, ?)";
            jdbcTemplate.update(sqlQuery,
                    film.getId(),
                    g.getId());
        });

        film.setGenres(getFilmGenres(film.getId()));
    }

    protected Map<String, Object> toMap(Film film) {
        Map<String, Object> values = new HashMap<>();
        values.put("name", film.getName());
        values.put("description", film.getDescription());
        values.put("release_date", film.getReleaseDate());
        values.put("duration", film.getDuration());
        values.put("mpa_id", film.getMpa().getId());
        return values;
    }
}
