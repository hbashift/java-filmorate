package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.LinkedHashSet;

public interface MpaStorage {
    LinkedHashSet<Mpa> getAllMpa();

    Mpa getMpaById(int id);
}
