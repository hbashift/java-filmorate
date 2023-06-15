package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mapper.MpaRowMapper;
import ru.yandex.practicum.filmorate.util.exception.NotFoundException;

import java.util.LinkedHashSet;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public LinkedHashSet<Mpa> getAllMpa() {
        LinkedHashSet<Mpa> mpaList = new LinkedHashSet<>();
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet("SELECT mpa_id, name FROM mpa");

        while (mpaRows.next()) {
            Mpa mpa = Mpa.builder()
                    .id(mpaRows.getInt("mpa_id"))
                    .name(mpaRows.getString("name"))
                    .build();
            mpaList.add(mpa);
        }
        return mpaList;
    }

    @Override
    public Mpa getMpaById(int id) {
        String sqlQuery = "SELECT mpa_id, name FROM mpa WHERE mpa_id=?";

        try {
            return jdbcTemplate.queryForObject(sqlQuery, new MpaRowMapper(), id);

        } catch (RuntimeException e) {
            throw new NotFoundException("Рейтинг mpa не найден.");
        }
    }
}
